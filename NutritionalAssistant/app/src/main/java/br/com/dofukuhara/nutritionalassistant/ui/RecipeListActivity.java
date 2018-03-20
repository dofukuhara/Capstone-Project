package br.com.dofukuhara.nutritionalassistant.ui;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Collections;

import br.com.dofukuhara.nutritionalassistant.R;
import br.com.dofukuhara.nutritionalassistant.adapter.RecipeAdapter;
import br.com.dofukuhara.nutritionalassistant.data.RecipeContract;
import br.com.dofukuhara.nutritionalassistant.model.Recipe;
import br.com.dofukuhara.nutritionalassistant.util.AdMobManager;
import br.com.dofukuhara.nutritionalassistant.util.RecipesNameComparator;
import br.com.dofukuhara.nutritionalassistant.util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListActivity extends AppCompatActivity implements RecipeAdapter.RecipeItemClickListener {

    private ArrayList<Recipe> mRecipeList;
    private RecipeAdapter mReciperAdapter;

    @BindView(R.id.fab_add_recipe)
    FloatingActionButton mFabAddRecipe;

    @BindView(R.id.rv_recipe_list)
    RecyclerView mRvRecipeList;

    @BindView(R.id.adViewRecipeList)
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        ButterKnife.bind(this);

        setTitle(R.string.activity_recipe_list);

        mAdView.loadAd(AdMobManager.getAdRequest());

        mRvRecipeList.setHasFixedSize(true);
        mRvRecipeList.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        mFabAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialogForNewRecipe();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadContentFromProvider();
        setLayoutParams(false);
    }

    private void loadContentFromProvider() {
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(RecipeContract.RecipeEntry.CONTENT_URI, null,
                null, null, null);

        mRecipeList = Utils.cursorToRecipeList(cursor);
    }

    private void setLayoutParams(boolean update) {
        Collections.sort(mRecipeList, new RecipesNameComparator());

        if (!update) {
            mReciperAdapter = new RecipeAdapter(this);
        }
        mReciperAdapter.setRecipeList(mRecipeList);

        mRvRecipeList.setAdapter(mReciperAdapter);
    }

    private void createDialogForNewRecipe() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_add_recipe, null))
            .setTitle(R.string.dialog_new_recipe_title)
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Dialog dialogObj =Dialog.class.cast(dialogInterface);
                    String newRecipeName = ((EditText) dialogObj
                            .findViewById(R.id.et_new_recipe_name))
                            .getText().toString().trim();

                    if (!TextUtils.isEmpty(newRecipeName)) {
                        createNewRecipe(newRecipeName);
                    } else {
                        showToast(R.string.recipe_empty_field_not_allowed);
                    }
                }
            })
            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).show();
    }

    private void createNewRecipe(String newRecipeName) {
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(RecipeContract.RecipeEntry.CONTENT_URI,
                null, RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME + "=?",
                new String[] {newRecipeName}, null);

        // If the Recipe is not a new one, lets notify the user
        if(cursor.moveToFirst()) {
            showToast(R.string.recipe_already_exists);
        } else {
            ContentValues cv = new ContentValues();
            cv.put(RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME, newRecipeName);

            Uri uri = cr.insert(RecipeContract.RecipeEntry.CONTENT_URI, cv);
            if (uri != null) {
                showToast(R.string.recipe_successfully_registered);

                loadContentFromProvider();
                setLayoutParams(true);
            } else {
                showToast(R.string.recipe_register_fail);
            }

        }
        cursor.close();
    }

    private void showToast(int message) {
        Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecipeItemClick(Recipe recipe) {
        // TODO: Implement Recipe Click Listener
        Toast.makeText(this, "Recipe Name: " + recipe.getRecipeName(), Toast.LENGTH_SHORT).show();
    }
}
