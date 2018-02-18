package br.com.dofukuhara.nutritionalassistant.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import br.com.dofukuhara.nutritionalassistant.R;
import br.com.dofukuhara.nutritionalassistant.adapter.CategoryAdapter;
import br.com.dofukuhara.nutritionalassistant.model.Category;
import br.com.dofukuhara.nutritionalassistant.network.TacoRestClient;
import br.com.dofukuhara.nutritionalassistant.util.CategoriesNameComparator;
import br.com.dofukuhara.nutritionalassistant.util.MenuOptionHandling;
import br.com.dofukuhara.nutritionalassistant.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryListActivity extends AppCompatActivity implements CategoryAdapter.CategoryItemClickListener {

    private ProgressBar mPbCategoryList;
    private RecyclerView mRvCategoryList;

    private ArrayList<Category> mCategoryList;
    private CategoryAdapter mCategoryAdapter;

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
                // TODO: Query via Content Provider in case that the user did not allowed network in Mobile Data
                getCategoryFromRest();
            } else {
                // In case that the screen was rotated, we can recover the list from Bundle
                mCategoryList = savedInstanceState
                        .getParcelableArrayList(Utils.CONST_BUNDLE_CATEGORY_LIST_PARCELABLE);

                mCategoryAdapter.setCategoryList(mCategoryList);
                mRvCategoryList.setAdapter(mCategoryAdapter);

            }
        }
    }

    private void layoutInitialization() {
        setTitle(R.string.activity_category);

        mPbCategoryList = findViewById(R.id.pb_category_list);
        mRvCategoryList = findViewById(R.id.rv_category_list);

        mRvCategoryList.setHasFixedSize(true);
        mRvCategoryList.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        mCategoryAdapter = new CategoryAdapter(this);
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

                mCategoryAdapter.setCategoryList(mCategoryList);
                mRvCategoryList.setAdapter(mCategoryAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                mPbCategoryList.setVisibility(View.GONE);

                // TODO: Handle error exception

            }
        });
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
        // TODO: Implement onClickListener from CategoryList
        Toast.makeText(this, category.getCategoryName(), Toast.LENGTH_SHORT).show();
    }
}
