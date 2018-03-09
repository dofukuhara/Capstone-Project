package br.com.dofukuhara.nutritionalassistant.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.dofukuhara.nutritionalassistant.R;
import br.com.dofukuhara.nutritionalassistant.model.Ingredient;
import br.com.dofukuhara.nutritionalassistant.network.TacoRestClient;
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

    @BindView(R.id.pb_ingredient_details)
    ProgressBar mLoadingBar;
    @BindView(R.id.img_btn_favorite)
    ImageButton mImgBtnFavorite;
    @BindView(R.id.tv_ingredient_name)
    TextView mTvIngredientName;

    @BindView(R.id.tv_ingredient_detail_category_label)
    TextView mTvIngredientCatLabel;
    @BindView(R.id.tv_ingredient_detail_category)
    TextView mTvIngredientCat;

    @BindView(R.id.tv_ingredient_detail_energy_label)
    TextView mTvIngredientEnergyLabel;
    @BindView(R.id.tv_ingredient_detail_energy)
    TextView mTvIngredientEnergy;

    @BindView(R.id.tv_ingredient_detail_protein_label)
    TextView mTvIngredientProtLabel;
    @BindView(R.id.tv_ingredient_detail_protein)
    TextView mTvIngredientProt;

    @BindView(R.id.tv_ingredient_detail_lipids_label)
    TextView mTvIngredientLipidsLabel;
    @BindView(R.id.tv_ingredient_detail_lipids)
    TextView mTvIngredientLipids;

    @BindView(R.id.tv_ingredient_detail_cholesterol_label)
    TextView mTvIngredientCholesterolLabel;
    @BindView(R.id.tv_ingredient_detail_cholesterol)
    TextView mTvIngredientCholesterol;

    @BindView(R.id.tv_ingredient_detail_carbohydrate_label)
    TextView mTvIngredientCarbohydrateLabel;
    @BindView(R.id.tv_ingredient_detail_carbohydrate)
    TextView mTvIngredientCarbohydrate;

    @BindView(R.id.tv_ingredient_detail_foodFiber_label)
    TextView mTvIngredientFoodFiberLabel;
    @BindView(R.id.tv_ingredient_detail_foodFiber)
    TextView mTvIngredientFoodFiber;

    @BindView(R.id.tv_ingredient_detail_ashes_label)
    TextView mTvIngredientAshesLabel;
    @BindView(R.id.tv_ingredient_detail_ashes)
    TextView mTvIngredientAshes;

    @BindView(R.id.tv_ingredient_detail_calcium_label)
    TextView mTvIngredientCalciumLabel;
    @BindView(R.id.tv_ingredient_detail_calcium)
    TextView mTvIngredientCalcium;

    @BindView(R.id.tv_ingredient_detail_magnesium_label)
    TextView mTvIngredientMagnesiumLabel;
    @BindView(R.id.tv_ingredient_detail_magnesium)
    TextView mTvIngredientMagnesium;

    @BindView(R.id.tv_ingredient_detail_manganese_label)
    TextView mTvIngredientManganeseLabel;
    @BindView(R.id.tv_ingredient_detail_manganese)
    TextView mTvIngredientManganese;

    @BindView(R.id.tv_ingredient_detail_phosphor_label)
    TextView mTvIngredientPhosphorLabel;
    @BindView(R.id.tv_ingredient_detail_phosphor)
    TextView mTvIngredientPhosphor;

    @BindView(R.id.tv_ingredient_detail_iron_label)
    TextView mTvIngredientIronLabel;
    @BindView(R.id.tv_ingredient_detail_iron)
    TextView mTvIngredientIron;

    @BindView(R.id.tv_ingredient_detail_sodium_label)
    TextView mTvIngredientSodiumLabel;
    @BindView(R.id.tv_ingredient_detail_sodium)
    TextView mTvIngredientSodium;

    @BindView(R.id.tv_ingredient_detail_potassium_label)
    TextView mTvIngredientPotassiumLabel;
    @BindView(R.id.tv_ingredient_detail_potassium)
    TextView mTvIngredientPotassium;

    @BindView(R.id.tv_ingredient_detail_copper_label)
    TextView mTvIngredientCopperLabel;
    @BindView(R.id.tv_ingredient_detail_copper)
    TextView mTvIngredientCopper;

    @BindView(R.id.tv_ingredient_detail_zinc_label)
    TextView mTvIngredientZincLabel;
    @BindView(R.id.tv_ingredient_detail_zinc)
    TextView mTvIngredientZinc;

    @BindView(R.id.tv_ingredient_detail_retinol_label)
    TextView mTvIngredientRetinolLabel;
    @BindView(R.id.tv_ingredient_detail_retinol)
    TextView mTvIngredientRetinol;

    @BindView(R.id.tv_ingredient_detail_re_label)
    TextView mTvIngredientReLabel;
    @BindView(R.id.tv_ingredient_detail_re)
    TextView mTvIngredientRe;

    @BindView(R.id.tv_ingredient_detail_rae_label)
    TextView mTvIngredientRaeLabel;
    @BindView(R.id.tv_ingredient_detail_rae)
    TextView mTvIngredientRae;

    @BindView(R.id.tv_ingredient_detail_thiamine_label)
    TextView mTvIngredientThiamineLabel;
    @BindView(R.id.tv_ingredient_detail_thiamine)
    TextView mTvIngredientThiamine;

    @BindView(R.id.tv_ingredient_detail_riboflavin_label)
    TextView mTvIngredientRiboLabel;
    @BindView(R.id.tv_ingredient_detail_riboflavin)
    TextView mTvIngredientRibo;

    @BindView(R.id.tv_ingredient_detail_pyridoxine_label)
    TextView mTvIngredientPyridLabel;
    @BindView(R.id.tv_ingredient_detail_pyridoxine)
    TextView mTvIngredientPyrid;

    @BindView(R.id.tv_ingredient_detail_niacin_label)
    TextView mTvIngredientNiacinLabel;
    @BindView(R.id.tv_ingredient_detail_niacin)
    TextView mTvIngredientNiacin;

    @BindView(R.id.tv_ingredient_detail_vitaminc_label)
    TextView mTvIngredientVitaCLabel;
    @BindView(R.id.tv_ingredient_detail_vitaminc)
    TextView mTvIngredientVitaC;

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

        // TODO: First, check if the food is in Content Provider, if not, fetch on the Internet

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
    }

    private void setLayoutComponent() {
        mImgBtnFavorite.setVisibility(View.VISIBLE);
        mTvIngredientCatLabel. setVisibility(View.VISIBLE);
        mTvIngredientEnergyLabel.setVisibility(View.VISIBLE);
        mTvIngredientProtLabel.setVisibility(View.VISIBLE);
        mTvIngredientLipidsLabel.setVisibility(View.VISIBLE);
        mTvIngredientCholesterolLabel.setVisibility(View.VISIBLE);
        mTvIngredientCarbohydrateLabel.setVisibility(View.VISIBLE);
        mTvIngredientFoodFiberLabel.setVisibility(View.VISIBLE);
        mTvIngredientAshesLabel.setVisibility(View.VISIBLE);
        mTvIngredientCalciumLabel.setVisibility(View.VISIBLE);
        mTvIngredientMagnesiumLabel.setVisibility(View.VISIBLE);
        mTvIngredientManganeseLabel.setVisibility(View.VISIBLE);
        mTvIngredientPhosphorLabel.setVisibility(View.VISIBLE);
        mTvIngredientIronLabel.setVisibility(View.VISIBLE);
        mTvIngredientSodiumLabel.setVisibility(View.VISIBLE);
        mTvIngredientPotassiumLabel.setVisibility(View.VISIBLE);
        mTvIngredientCopperLabel.setVisibility(View.VISIBLE);
        mTvIngredientZincLabel.setVisibility(View.VISIBLE);
        mTvIngredientRetinolLabel.setVisibility(View.VISIBLE);
        mTvIngredientReLabel.setVisibility(View.VISIBLE);
        mTvIngredientRaeLabel.setVisibility(View.VISIBLE);
        mTvIngredientThiamineLabel.setVisibility(View.VISIBLE);
        mTvIngredientRiboLabel.setVisibility(View.VISIBLE);
        mTvIngredientPyridLabel.setVisibility(View.VISIBLE);
        mTvIngredientNiacinLabel.setVisibility(View.VISIBLE);
        mTvIngredientVitaCLabel.setVisibility(View.VISIBLE);

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
