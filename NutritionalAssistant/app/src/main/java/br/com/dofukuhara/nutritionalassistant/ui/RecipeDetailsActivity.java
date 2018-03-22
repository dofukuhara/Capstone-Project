package br.com.dofukuhara.nutritionalassistant.ui;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;

import br.com.dofukuhara.nutritionalassistant.R;
import br.com.dofukuhara.nutritionalassistant.data.RecipeContract;
import br.com.dofukuhara.nutritionalassistant.model.Recipe;
import br.com.dofukuhara.nutritionalassistant.util.AdMobManager;
import br.com.dofukuhara.nutritionalassistant.util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity {

    private Recipe mRecipe;
    private Context mContext;

    @BindView(R.id.adViewRecipeDetails)
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        ButterKnife.bind(this);

        getValueFromIntent();

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mContext = this;

        mAdView.loadAd(AdMobManager.getAdRequest());

        setTitle(mRecipe.getRecipeName());
    }

    private void getValueFromIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(Utils.CONST_INTENT_RECIPE)) {
            mRecipe = intent.getParcelableExtra(Utils.CONST_INTENT_RECIPE);
        } else {
            // If no extra was passed via Intent, let's close this activity
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recipe_custom_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                deleteThisRecipe();
                break;
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteThisRecipe() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.are_you_sure_delete_recipe_title)
            .setMessage(R.string.are_you_sure_delete_recipe_msg)
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ContentResolver cr = getContentResolver();
                    Uri uri = ContentUris.withAppendedId(
                            RecipeContract.RecipeEntry.CONTENT_URI,
                            mRecipe.getRecipeId());
                    int numberDeletedItems = cr.delete(uri, null, null);

                    if (numberDeletedItems > 0) {
                        Toast.makeText(mContext,
                                mContext.getString(R.string.recipe_delete_success),
                                Toast.LENGTH_SHORT).show();

                        // After successfully removed this recipe, let' return to the previous activity
                        finish();
                    } else {
                        Toast.makeText(mContext,
                                mContext.getString(R.string.recipe_fail_delete),
                                Toast.LENGTH_SHORT).show();

                    }

                }
            })
            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).show();
    }
}
