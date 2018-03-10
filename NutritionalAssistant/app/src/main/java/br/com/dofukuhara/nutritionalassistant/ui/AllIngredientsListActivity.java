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

import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Collections;

import br.com.dofukuhara.nutritionalassistant.R;
import br.com.dofukuhara.nutritionalassistant.adapter.IngredientsStubListAdapter;
import br.com.dofukuhara.nutritionalassistant.model.IngredientStub;
import br.com.dofukuhara.nutritionalassistant.network.TacoRestClient;
import br.com.dofukuhara.nutritionalassistant.util.AdMobManager;
import br.com.dofukuhara.nutritionalassistant.util.IngredientsStubNameComparator;
import br.com.dofukuhara.nutritionalassistant.util.MenuOptionHandling;
import br.com.dofukuhara.nutritionalassistant.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllIngredientsListActivity extends AppCompatActivity implements IngredientsStubListAdapter.IngredientItemClickListener {

    private ProgressBar mPbAllIngredientsList;
    private RecyclerView mRvAllIngredientsList;

    private ArrayList<IngredientStub> mIngredientsList;
    private IngredientsStubListAdapter mIngredientsStubListAdapter;

    private boolean isMobileDataAllowed;

    private AdView mAdView;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO: Due to the large size of the list, a TransactionTooLargeException exception may be thrown, so we need to change this...

        if (mIngredientsList != null) {
            outState.putParcelableArrayList(Utils.CONST_BUNDLE_ALL_INGREDIENTS_LIST_PARCELABLE,
                    mIngredientsList);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_ingredients_list);

        SharedPreferences sharedPref = getSharedPreferences(
                Utils.SHARED_PREF_KEY, Context.MODE_PRIVATE);

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
                !savedInstanceState.containsKey(Utils.CONST_BUNDLE_ALL_INGREDIENTS_LIST_PARCELABLE)) {
            // If the list is not presented in the Bundle, lets get it from Content Provider
            // or via Rest
            // TODO: Query via Content Provider in case that the user did not allowed network in Mobile Data
            getIngredientsFromRest();
        } else {
            // In case that the screen was rotated, we can recover the list from Bundle
            mIngredientsList = savedInstanceState
                    .getParcelableArrayList(Utils.CONST_BUNDLE_ALL_INGREDIENTS_LIST_PARCELABLE);

            mIngredientsStubListAdapter.setIngredientsList(mIngredientsList);
            mRvAllIngredientsList.setAdapter(mIngredientsStubListAdapter);

        }
    }

    private void layoutInitialization() {
        setTitle(R.string.activity_all_ingredients);

        mAdView = findViewById(R.id.adViewAllIngredientsList);
        mAdView.loadAd(AdMobManager.getAdRequest());

        mPbAllIngredientsList = findViewById(R.id.pb_all_ingredients_list);
        mRvAllIngredientsList = findViewById(R.id.rv_all_ingredients_list);

        mRvAllIngredientsList.setHasFixedSize(true);
        mRvAllIngredientsList.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        mIngredientsStubListAdapter = new IngredientsStubListAdapter(this);
    }

    private void getIngredientsFromRest() {
        mPbAllIngredientsList.setVisibility(View.VISIBLE);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url_for_taco))
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        TacoRestClient client = retrofit.create(TacoRestClient.class);

        Call<ArrayList<IngredientStub>> call = client.getListOfIngredients();

        call.enqueue(new Callback<ArrayList<IngredientStub>>() {
            @Override
            public void onResponse(Call<ArrayList<IngredientStub>> call, Response<ArrayList<IngredientStub>> response) {
                mPbAllIngredientsList.setVisibility(View.GONE);

                mIngredientsList = response.body();

                Collections.sort(mIngredientsList, new IngredientsStubNameComparator());

                mIngredientsStubListAdapter.setIngredientsList(mIngredientsList);
                mRvAllIngredientsList.setAdapter(mIngredientsStubListAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<IngredientStub>> call, Throwable t) {
                mPbAllIngredientsList.setVisibility(View.GONE);

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
    public void onIngredientItemClick(IngredientStub ingredient) {
        Intent intent = new Intent(this, IngredientDetailsActivity.class);
        intent.putExtra(Utils.CONST_INTENT_INGREDIENT_ID, ingredient.getId());

        startActivity(intent);
    }
}
