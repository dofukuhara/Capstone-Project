package br.com.dofukuhara.nutritionalassistant.ui;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Collections;

import br.com.dofukuhara.nutritionalassistant.R;
import br.com.dofukuhara.nutritionalassistant.adapter.CategoryListAdapter;
import br.com.dofukuhara.nutritionalassistant.data.CategoryContract;
import br.com.dofukuhara.nutritionalassistant.model.Category;
import br.com.dofukuhara.nutritionalassistant.network.TacoRestClient;
import br.com.dofukuhara.nutritionalassistant.util.AdMobManager;
import br.com.dofukuhara.nutritionalassistant.util.CategoriesNameComparator;
import br.com.dofukuhara.nutritionalassistant.util.MenuOptionHandling;
import br.com.dofukuhara.nutritionalassistant.util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryListActivity extends AppCompatActivity implements CategoryListAdapter.CategoryItemClickListener {

    @BindView(R.id.pb_category_list)
    ProgressBar mPbCategoryList;

    @BindView(R.id.rv_category_list)
    RecyclerView mRvCategoryList;

    @BindView(R.id.adViewCategoryList)
    AdView mAdView;

    private ArrayList<Category> mCategoryList;
    private CategoryListAdapter mCategoryAdapter;

    private boolean isMobileDataAllowed;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mCategoryList != null) {
           outState.putParcelableArrayList(Utils.CONST_BUNDLE_CATEGORY_LIST_PARCELABLE,
                   mCategoryList);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        ButterKnife.bind(this);

        SharedPreferences sharedPref = getSharedPreferences(
                Utils.SHARED_PREF_KEY, Context.MODE_PRIVATE);
        if (!sharedPref.contains(Utils.CONST_SHARED_PREF_ALREADY_OPENED_APP)) {
            // If this is the first time that the Application was opened,
            // we will send the user to the configuration activity first, prior to use the app
            Intent intent = new Intent(this, ConfigurationActivity.class);
            intent.putExtra(Utils.CONST_CONFIG_SETUP_WIZARD, true);
            startActivity(intent);

            finish();
        } else {

            layoutInitialization();

            // Lets check if the user allowed us to use Mobile Data network, by checking the
            // configuration from SharedPref. If the value, by somehow, is not there, initialize
            // with the default value.
            if (sharedPref.contains(Utils.CONST_SHARED_PREF_IS_MOBILE_DATA_ALLOWED)) {
                isMobileDataAllowed = sharedPref.getBoolean(
                        Utils.CONST_SHARED_PREF_IS_MOBILE_DATA_ALLOWED,
                        getResources().getBoolean(R.bool.default_is_mobile_data_allowed));
            } else {
                isMobileDataAllowed = getResources().getBoolean(R.bool.default_is_mobile_data_allowed);
            }

            if (savedInstanceState == null ||
                    !savedInstanceState.containsKey(Utils.CONST_BUNDLE_CATEGORY_LIST_PARCELABLE)) {
                // If the list is not presented in the Bundle, lets get it from Content Provider
                // or via Rest
                // TODO: (1) Query via Content Provider in case that the user did not allowed network in Mobile Data
                // TODO: (2) Check the case where we need to refresh the content of Category info in the Provider

                // Query Content Provider for Categories stored on it
                Cursor cursor = getContentResolver().query(
                        CategoryContract.CategoryEntry.CONTENT_URI, null,null, null,null);

                // Get an ArrayList of Category from a cursor
                mCategoryList = Utils.cursorToCategoryArrayList(cursor);
                if (cursor != null) {
                    cursor.close();
                }

                if (mCategoryList.size() == 0) {
                    // In case that the list is empty, we need to fetch the data from the internet
                    // TODO: need to check if we are using Mobile Data and if user allowed us to do so
                    getCategoryFromRest();
                } else {
                    // In case that the data was found in the provider...
                    // ... lets sort it alphabetically first
                    Collections.sort(mCategoryList, new CategoriesNameComparator());
                    // ... and them display it to the user
                    updateAdapter();
                }

            } else {
                // In case that the screen was rotated, we can recover the list from Bundle
                mCategoryList = savedInstanceState
                        .getParcelableArrayList(Utils.CONST_BUNDLE_CATEGORY_LIST_PARCELABLE);

                updateAdapter();
            }
        }
    }

    private void layoutInitialization() {
        setTitle(R.string.activity_category);

        mAdView.loadAd(AdMobManager.getAdRequest());

        mRvCategoryList.setHasFixedSize(true);
        mRvCategoryList.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        mCategoryAdapter = new CategoryListAdapter(this);
    }

    private void getCategoryFromRest() {
        mPbCategoryList.setVisibility(View.VISIBLE);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url_for_taco))
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        TacoRestClient client = retrofit.create(TacoRestClient.class);

        Call<ArrayList<Category>> call = client.getListOfCategories();

        call.enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                mPbCategoryList.setVisibility(View.GONE);

                mCategoryList = response.body();

                Collections.sort(mCategoryList, new CategoriesNameComparator());

                // Saving the content of the Downloaded data in the provider in another Thread
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ContentResolver cr = getContentResolver();
                        for (Category category : mCategoryList) {
                            ContentValues cv = new ContentValues();
                            cv.put(CategoryContract.CategoryEntry.COLUMN_CATEGORY_ID, category.getId());
                            cv.put(CategoryContract.CategoryEntry.COLUMN_CATEGORY_NAME, category.getCategoryName());

                            cr.insert(CategoryContract.CategoryEntry.CONTENT_URI, cv);
                        }
                    }
                }).start();

                updateAdapter();
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                mPbCategoryList.setVisibility(View.GONE);

                // TODO: Handle error exception

            }
        });
    }

    private void updateAdapter() {
        mCategoryAdapter.setCategoryList(mCategoryList);
        mRvCategoryList.setAdapter(mCategoryAdapter);

        // Adding a divider between lines for a better visualization
        mRvCategoryList.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_list_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItem = item.getItemId();

        MenuOptionHandling.performAction(selectedItem, this);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCategoryItemClick(Category category) {
        // Intent to open IngredientsByCategory with all Ingredients based on the selected food Category
        Intent intent = new Intent(this, IngredientsByCategoryActivity.class);
        intent.putExtra(Utils.CONST_INTENT_CATEGORY, category);

        startActivity(intent);
    }
}
