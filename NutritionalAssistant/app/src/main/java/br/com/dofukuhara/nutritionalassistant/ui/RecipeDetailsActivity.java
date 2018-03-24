package br.com.dofukuhara.nutritionalassistant.ui;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Collections;

import br.com.dofukuhara.nutritionalassistant.R;
import br.com.dofukuhara.nutritionalassistant.adapter.RecipeDetailsAdapter;
import br.com.dofukuhara.nutritionalassistant.data.IngredientContract;
import br.com.dofukuhara.nutritionalassistant.data.RecipeContract;
import br.com.dofukuhara.nutritionalassistant.model.Ingredient;
import br.com.dofukuhara.nutritionalassistant.model.Recipe;
import br.com.dofukuhara.nutritionalassistant.util.AdMobManager;
import br.com.dofukuhara.nutritionalassistant.util.IngredientNameComparator;
import br.com.dofukuhara.nutritionalassistant.util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailsAdapter.IngredientItemClickListener {

    private Recipe mRecipe;
    private Context mContext;
    private ArrayList<Ingredient> mIngredientList;
    private RecipeDetailsAdapter mRecipeDetailsAdapter;

    @BindView(R.id.rv_recipe_details)
    RecyclerView mRvRecipeDetails;

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


        mRecipeDetailsAdapter = new RecipeDetailsAdapter(this);

        mRvRecipeDetails.setHasFixedSize(true);
        mRvRecipeDetails.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false));
        mRvRecipeDetails.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mIngredientList = getIngredListFromRecipe();
        if(mIngredientList != null) {
            Collections.sort(mIngredientList, new IngredientNameComparator());
            mRecipeDetailsAdapter.setIngredientList(mIngredientList);
        }

        mRvRecipeDetails.setAdapter(mRecipeDetailsAdapter);
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

    private ArrayList<Ingredient> getIngredListFromRecipe() {
        if (mRecipe.getRecipeIngredients() == null) {
            return null;
        } else {
            ArrayList<Ingredient> retList = new ArrayList<>();
            String[] ingredIds = TextUtils.split(mRecipe.getRecipeIngredients(), ",");

            StringBuilder numberOfIngredients = new StringBuilder("?");
            for (int i = 1; i < ingredIds.length; i++) {
                numberOfIngredients.append(",?");
            }

            ContentResolver cr = getContentResolver();
            Uri uri = IngredientContract.IngredientEntry.CONTENT_URI;
            Cursor cursor = cr.query(uri, null,
                    IngredientContract.IngredientEntry.COLUMN_INGREDIENT_ID +
                            " IN(" + numberOfIngredients + ")",
                    ingredIds, null);

            while (cursor.moveToNext()) {
                retList.add(Utils.cursorToIngredient(cursor));
            }

            return retList;
        }
    }

    @Override
    public void onIngredientItemClick(boolean toDelete, Ingredient ingredient) {
        if (ingredient == null) {
            Toast.makeText(this, "Add new Item!", Toast.LENGTH_SHORT).show();
        } else {
            if (toDelete) {
                deleteIngFromRecipe(ingredient);
            } else {
                Intent intent = new Intent(this, IngredientDetailsActivity.class);
                intent.putExtra(Utils.CONST_INTENT_INGREDIENT_ID, ingredient.getId());

                startActivity(intent);
            }
        }
    }

    private void deleteIngFromRecipe(final Ingredient ingredient) {
        // Get the ID of the ingredient and convert to String
        String ingredientId = String.valueOf(ingredient.getId());

        // A backup of the IngredientList, to be used when the UNDO button is clicked
        final String backupOfIngredList = mRecipe.getRecipeIngredients();

        // Convert the String to String array in order to remove the selected Ingredient from the list
        String[] ingredList = TextUtils.split(mRecipe.getRecipeIngredients(),",");
        for (int i = 0; i < ingredList.length; i++) {
            if(ingredientId.equals(ingredList[i])) {
                ingredList[i]=null;
            }
        }

        final ContentResolver cr = getContentResolver();
        final Uri baseRecipeUri = RecipeContract.RecipeEntry.CONTENT_URI;

        // Create a ContentValue to update the current Recipe by removing the selected ingredient
        ContentValues cv = new ContentValues();
        if(ingredList.length -1 == 0) {
            cv.putNull(RecipeContract.RecipeEntry.COLUMN_RECIPE_INGREDIENTS);
            mRecipe.setRecipeIngredients(null);
        } else if (ingredList.length -1 == 1) {
            String newValue = ingredList[0] != null ? ingredList[0] : ingredList[1];
            cv.put(RecipeContract.RecipeEntry.COLUMN_RECIPE_INGREDIENTS,
                    newValue);
            mRecipe.setRecipeIngredients(newValue);
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < ingredList.length; i++) {
                if(ingredList[i] != null) {
                    sb.append(ingredList[i]).append(",");
                }
            }
            sb.deleteCharAt(sb.length() - 1);

            cv.put(RecipeContract.RecipeEntry.COLUMN_RECIPE_INGREDIENTS,
                    sb.toString());
            mRecipe.setRecipeIngredients(sb.toString());
        }

        // Update the Recipe with the new Ingredient List
        cr.update(baseRecipeUri,
                cv,
                RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME + "=?",
                new String[]{mRecipe.getRecipeName()});

        // Generating the SnackBar with the UNDO option
        Snackbar snackbar = Snackbar.make(
                findViewById(R.id.recipe_details_constraint_layout),
                R.string.remove_from_recipe_success, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.undo,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // In case that the UNDO is pressed...

                                // ... generate a ContentValue with the list that was backed-up
                                ContentValues updateList = new ContentValues();
                                updateList.put(
                                        RecipeContract.RecipeEntry.COLUMN_RECIPE_INGREDIENTS,
                                        backupOfIngredList
                                );
                                // and update the Recipe obj as well
                                mRecipe.setRecipeIngredients(backupOfIngredList);

                                // Update the Recipe record in the Content Provider
                                cr.update(baseRecipeUri,
                                        updateList,
                                        RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME + "=?",
                                        new String[]{mRecipe.getRecipeName()});

                                // In order to not fetch the Provider again, lets manually re-include the
                                // ingredient back in the local list again ...
                                mIngredientList.add(ingredient);

                                // ...and display it to the end-user
                                Collections.sort(mIngredientList, new IngredientNameComparator());
                                mRecipeDetailsAdapter.setIngredientList(mIngredientList);
                                mRvRecipeDetails.setAdapter(mRecipeDetailsAdapter);
                            }
                        });
        snackbar.show();

        // After removing the ingredient from the Recipe, instead of fetching again the provider,
        // lets just manually remove this ingredient from the local list ...
        mIngredientList.remove(ingredient);

        // ...and display it to the end-user
        mRecipeDetailsAdapter.setIngredientList(mIngredientList);
        mRvRecipeDetails.setAdapter(mRecipeDetailsAdapter);
    }
}
