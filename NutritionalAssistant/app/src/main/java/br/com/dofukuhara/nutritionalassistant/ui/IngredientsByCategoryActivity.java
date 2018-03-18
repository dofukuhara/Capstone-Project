package br.com.dofukuhara.nutritionalassistant.ui;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Collections;

import br.com.dofukuhara.nutritionalassistant.R;
import br.com.dofukuhara.nutritionalassistant.adapter.IngredientsStubListAdapter;
import br.com.dofukuhara.nutritionalassistant.data.IngredientStubContract;
import br.com.dofukuhara.nutritionalassistant.model.Category;
import br.com.dofukuhara.nutritionalassistant.model.IngredientStub;
import br.com.dofukuhara.nutritionalassistant.network.TacoRestClient;
import br.com.dofukuhara.nutritionalassistant.util.AdMobManager;
import br.com.dofukuhara.nutritionalassistant.util.IngredientsStubNameComparator;
import br.com.dofukuhara.nutritionalassistant.util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IngredientsByCategoryActivity extends AppCompatActivity implements IngredientsStubListAdapter.IngredientItemClickListener{

    private Category mCategory;
    private ArrayList<IngredientStub> mIngredientStubList;

    @BindView(R.id.pg_ingred_by_categ)
    ProgressBar mPgIngredByCateg;

    @BindView(R.id.tv_category_name)
    TextView mTvCategoryName;

    @BindView(R.id.rv_ingd_list_by_categ)
    RecyclerView mRvIngdListByCateg;

    @BindView(R.id.adViewIngredListByCateg)
    AdView mAdView;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mIngredientStubList != null) {
            outState.putParcelableArrayList(Utils.CONST_BUNDLE_ALL_INGREDIENTS_LIST_PARCELABLE,
                    mIngredientStubList);
        }
        if (mCategory != null) {
            outState.putParcelable(Utils.CONST_BUNDLE_CATEGORY_BUNDLE, mCategory);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_by_category);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ButterKnife.bind(this);

        setTitle(R.string.activity_ingredients_by_category);

        if (savedInstanceState != null &&
                savedInstanceState.containsKey(Utils.CONST_BUNDLE_ALL_INGREDIENTS_LIST_PARCELABLE) &&
                savedInstanceState.containsKey(Utils.CONST_BUNDLE_CATEGORY_BUNDLE)) {
            // In case that this activity is being draw after a screen orientation change, instead
            // of retrieving the data again from ContentProvider, lets just obtain it from the bundle
            mIngredientStubList =
                    savedInstanceState.getParcelableArrayList(Utils.CONST_BUNDLE_ALL_INGREDIENTS_LIST_PARCELABLE);
            mCategory = savedInstanceState.getParcelable(Utils.CONST_BUNDLE_CATEGORY_BUNDLE);

            loadLayoutContents();
        } else {
            // ... otherwise, we need to the get Category ID from the Intent and query the Content Provider
            loadContentFromIntent();
        }
    }

    private void loadLayoutContents() {
        mPgIngredByCateg.setVisibility(View.GONE);
        mAdView.loadAd(AdMobManager.getAdRequest());

        mTvCategoryName.setText(mCategory.getCategoryName());
        mTvCategoryName.setVisibility(View.VISIBLE);

        mRvIngdListByCateg.setHasFixedSize(true);
        mRvIngdListByCateg.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        IngredientsStubListAdapter mIngredientStubAdapter = new IngredientsStubListAdapter(this);
        mIngredientStubAdapter.setIngredientsList(mIngredientStubList);

        mRvIngdListByCateg.setAdapter(mIngredientStubAdapter);
    }

    private void loadContentFromIntent() {
        // TODO: If no IngredientStub list is yet stored in the Provider, we should fetch from the Internet

        // Display Progress Bar to the user while we query all the Ingredients...
        mPgIngredByCateg.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        mCategory = intent.getParcelableExtra(Utils.CONST_INTENT_CATEGORY);

        ContentResolver cr = getContentResolver();
        Uri uri = IngredientStubContract.BASE_CONTENT_URI
                .buildUpon().appendEncodedPath(IngredientStubContract.INGREDIENT_STUB_CLASS_PATH)
                .build();
        Uri uriForQuery = ContentUris.withAppendedId(uri, Long.valueOf(mCategory.getId()));
        Cursor cursor = cr.query(uriForQuery, null, null, null, null);
        mIngredientStubList = Utils.cursorToIngredientStubList(cursor);

        if (mIngredientStubList.size() > 0) {
            Collections.sort(mIngredientStubList, new IngredientsStubNameComparator());

            loadLayoutContents();
        } else {
            getIngredientsFromRest();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onIngredientItemClick(IngredientStub ingredient) {
        Intent intent = new Intent(this, IngredientDetailsActivity.class);
        intent.putExtra(Utils.CONST_INTENT_INGREDIENT_ID, ingredient.getId());

        startActivity(intent);
    }

    public void getIngredientsFromRest() {
        mPgIngredByCateg.setVisibility(View.VISIBLE);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url_for_taco))
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        TacoRestClient client = retrofit.create(TacoRestClient.class);

        Call<ArrayList<IngredientStub>> call = client.getListOfIngredients();

        call.enqueue(new Callback<ArrayList<IngredientStub>>() {
            @Override
            public void onResponse(Call<ArrayList<IngredientStub>> call, Response<ArrayList<IngredientStub>> response) {
                ArrayList<IngredientStub> mIngredientsList;

                mIngredientsList = response.body();

                ContentResolver cr = getContentResolver();
                for (IngredientStub stub : mIngredientsList) {
                    ContentValues cv = new ContentValues();
                    cv.put(IngredientStubContract.IngredientStubEntry.COLUMN_INGREDIENT_ID,
                            stub.getId());
                    cv.put(IngredientStubContract.IngredientStubEntry.COLUMN_INGREDIENT_DESCRIPT,
                            stub.getDescription());
                    cv.put(IngredientStubContract.IngredientStubEntry.COLUMN_INGREDIENT_CLASSIF,
                            stub.getClassification());

                    cr.insert(IngredientStubContract.IngredientStubEntry.CONTENT_URI, cv);

                    // As we are iterating the ArrayList to save the IngredientStub in the Provider,
                    // lets use this to filter the Ingredients by Category in order to populate
                    // mIngredientStubList, instead of querying ContentProvider
                    if(stub.getClassification().equals(String.valueOf(mCategory.getId()))) {
                        mIngredientStubList.add(stub);
                    }
                }

                if (mIngredientStubList.size() > 0) {
                    Collections.sort(mIngredientStubList, new IngredientsStubNameComparator());

                    loadLayoutContents();
                } else {
                    // TODO: Handle case where no Ingredients were retrieved from the Internet
                    mPgIngredByCateg.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<IngredientStub>> call, Throwable t) {
                mPgIngredByCateg.setVisibility(View.GONE);

                // TODO: Handle error exception
            }
        });
    }
}
