package br.com.dofukuhara.nutritionalassistant.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import br.com.dofukuhara.nutritionalassistant.R;
import br.com.dofukuhara.nutritionalassistant.model.Ingredient;
import br.com.dofukuhara.nutritionalassistant.network.TacoRestClient;
import br.com.dofukuhara.nutritionalassistant.util.AdMobManager;
import br.com.dofukuhara.nutritionalassistant.util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IngredientDetailsActivity extends AppCompatActivity {

    private Context mContext;
    private int mIngredientId;
    private ArrayList<Ingredient> mIngredientList;
    private Ingredient mIngredient;

    private AdView mAdView;

    @BindView(R.id.sv_ingredients_details)
    ScrollView mSvIngredientsDetails;

    @BindView(R.id.cl_ingredient_details)
    ConstraintLayout mClIngredientDetails;

    @BindView(R.id.pb_ingredient_details)
    ProgressBar mLoadingBar;
    @BindView(R.id.img_btn_favorite)
    ImageButton mImgBtnFavorite;
    @BindView(R.id.tv_ingredient_name)
    TextView mTvIngredientName;

    @BindView(R.id.tv_ingredient_detail_category)
    TextView mTvIngredientCat;

    @BindView(R.id.tv_ingredient_detail_energy)
    TextView mTvIngredientEnergy;

    @BindView(R.id.tv_ingredient_detail_protein)
    TextView mTvIngredientProt;

    @BindView(R.id.tv_ingredient_detail_lipids)
    TextView mTvIngredientLipids;

    @BindView(R.id.tv_ingredient_detail_cholesterol)
    TextView mTvIngredientCholesterol;

    @BindView(R.id.tv_ingredient_detail_carbohydrate)
    TextView mTvIngredientCarbohydrate;

    @BindView(R.id.tv_ingredient_detail_foodFiber)
    TextView mTvIngredientFoodFiber;

    @BindView(R.id.tv_ingredient_detail_ashes)
    TextView mTvIngredientAshes;

    @BindView(R.id.tv_ingredient_detail_calcium)
    TextView mTvIngredientCalcium;

    @BindView(R.id.tv_ingredient_detail_magnesium)
    TextView mTvIngredientMagnesium;

    @BindView(R.id.tv_ingredient_detail_manganese)
    TextView mTvIngredientManganese;

    @BindView(R.id.tv_ingredient_detail_phosphor)
    TextView mTvIngredientPhosphor;

    @BindView(R.id.tv_ingredient_detail_iron)
    TextView mTvIngredientIron;

    @BindView(R.id.tv_ingredient_detail_sodium)
    TextView mTvIngredientSodium;

    @BindView(R.id.tv_ingredient_detail_potassium)
    TextView mTvIngredientPotassium;

    @BindView(R.id.tv_ingredient_detail_copper)
    TextView mTvIngredientCopper;

    @BindView(R.id.tv_ingredient_detail_zinc)
    TextView mTvIngredientZinc;

    @BindView(R.id.tv_ingredient_detail_retinol)
    TextView mTvIngredientRetinol;

    @BindView(R.id.tv_ingredient_detail_re)
    TextView mTvIngredientRe;

    @BindView(R.id.tv_ingredient_detail_rae)
    TextView mTvIngredientRae;

    @BindView(R.id.tv_ingredient_detail_thiamine)
    TextView mTvIngredientThiamine;

    @BindView(R.id.tv_ingredient_detail_riboflavin)
    TextView mTvIngredientRibo;

    @BindView(R.id.tv_ingredient_detail_pyridoxine)
    TextView mTvIngredientPyrid;

    @BindView(R.id.tv_ingredient_detail_niacin)
    TextView mTvIngredientNiacin;

    @BindView(R.id.tv_ingredient_detail_vitaminc)
    TextView mTvIngredientVitaC;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mIngredient != null) {
            outState.putParcelable(Utils.CONST_BUNDLE_INGREDIENT_PARCELABLE,
                    mIngredient);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_details);

        Intent intent = getIntent();
        if (intent.hasExtra(Utils.CONST_INTENT_INGREDIENT_ID)) {
            mIngredientId = intent.getIntExtra(Utils.CONST_INTENT_INGREDIENT_ID, 0);
        } else {
            // TODO: If there is no value from intent, we can close this activity?
            finish();
        }

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ButterKnife.bind(this);

        mContext = this;
        setTitle(getString(R.string.activity_ingredient_details));

        mAdView = findViewById(R.id.adViewIngredientDetails);
        mAdView.loadAd(AdMobManager.getAdRequest());

        // TODO: First, check if the food is in Content Provider, if not, fetch on the Internet

        loadIngredientContent(savedInstanceState);
    }

    private void loadIngredientContent(Bundle savedInstanceState) {
        if (savedInstanceState == null ||
                !savedInstanceState.containsKey(Utils.CONST_BUNDLE_INGREDIENT_PARCELABLE)) {
            showLoadingBar(true);

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(getString(R.string.base_url_for_taco))
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();

            TacoRestClient client = retrofit.create(TacoRestClient.class);

            Call<ArrayList<Ingredient>> call = client.getIngredientById(mIngredientId);

            call.enqueue(new Callback<ArrayList<Ingredient>>() {
                @Override
                public void onResponse(Call<ArrayList<Ingredient>> call, Response<ArrayList<Ingredient>> response) {
                    showLoadingBar(false);

                    mIngredientList = response.body();
                    mIngredient = mIngredientList.get(0);

                    // TODO: Salvar o resultado obtido no Content Provider

                    setLayoutComponent();
                }

                @Override
                public void onFailure(Call<ArrayList<Ingredient>> call, Throwable t) {
                    showLoadingBar(false);
                }
            });
        } else {
            mIngredient = savedInstanceState.getParcelable(
                    Utils.CONST_BUNDLE_INGREDIENT_PARCELABLE
            );

            setLayoutComponent();
        }
    }

    private void setLayoutComponent() {
        mTvIngredientName.setText(mIngredient.getDescription());
        // TODO: Change Classification from number to a correct correlation
        mTvIngredientCat.setText(mIngredient.getClassification());
        mTvIngredientEnergy.setText(mIngredient.getEnergyKcal());
        mTvIngredientProt.setText(mIngredient.getProtein());
        mTvIngredientLipids.setText(mIngredient.getLipids());
        mTvIngredientCholesterol.setText(mIngredient.getCholesterol());
        mTvIngredientCarbohydrate.setText(mIngredient.getCarbohydrate());
        mTvIngredientFoodFiber.setText(mIngredient.getFoodFiber());
        mTvIngredientAshes.setText(mIngredient.getAshes());
        mTvIngredientCalcium.setText(mIngredient.getCalcium());
        mTvIngredientMagnesium.setText(mIngredient.getMagnesium());
        mTvIngredientManganese.setText(mIngredient.getManganese());
        mTvIngredientPhosphor.setText(mIngredient.getPhosphor());
        mTvIngredientIron.setText(mIngredient.getIron());
        mTvIngredientSodium.setText(mIngredient.getSodium());
        mTvIngredientPotassium.setText(mIngredient.getPotassium());
        mTvIngredientCopper.setText(mIngredient.getCopper());
        mTvIngredientZinc.setText(mIngredient.getZinc());
        mTvIngredientRetinol.setText(mIngredient.getRetinol());
        mTvIngredientRe.setText(mIngredient.getRe());
        mTvIngredientRae.setText(mIngredient.getRae());
        mTvIngredientThiamine.setText(mIngredient.getThiamine());
        mTvIngredientRibo.setText(mIngredient.getRiboflavin());
        mTvIngredientPyrid.setText(mIngredient.getPyridoxine());
        mTvIngredientNiacin.setText(mIngredient.getNiacin());
        mTvIngredientVitaC.setText(mIngredient.getVitaminC());

        mSvIngredientsDetails.setVisibility(View.VISIBLE);
        mClIngredientDetails.setVisibility(View.VISIBLE);
    }

    private void showLoadingBar(boolean toBeShown) {
        if (toBeShown) {
            mLoadingBar.setVisibility(View.VISIBLE);
        } else {
            mLoadingBar.setVisibility(View.GONE);
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
}
