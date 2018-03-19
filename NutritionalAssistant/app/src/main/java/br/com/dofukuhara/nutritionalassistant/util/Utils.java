package br.com.dofukuhara.nutritionalassistant.util;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import br.com.dofukuhara.nutritionalassistant.data.CategoryContract;
import br.com.dofukuhara.nutritionalassistant.data.FavoriteContract;
import br.com.dofukuhara.nutritionalassistant.data.IngredientContract;
import br.com.dofukuhara.nutritionalassistant.data.IngredientStubContract;
import br.com.dofukuhara.nutritionalassistant.model.Category;
import br.com.dofukuhara.nutritionalassistant.model.Favorite;
import br.com.dofukuhara.nutritionalassistant.model.Ingredient;
import br.com.dofukuhara.nutritionalassistant.model.IngredientStub;

/**
 * Created by dofukuhara on 14/02/2018.
 */

public final class Utils {

    /* Consts for Shared Preferences */
    public static final String SHARED_PREF_KEY = "SHARED_PREF_NUTRI_ASSIST_KEY";
    // Shared pref for launch Configuration activity when app is opened for the first time
    public static final String CONST_SHARED_PREF_ALREADY_OPENED_APP =
            "const_shared_pref_already_opened_app";
    // Shared pref with Mobile Data option
    public static final String CONST_SHARED_PREF_IS_MOBILE_DATA_ALLOWED =
            "const_shared_pref_is_mobile_data_allowed";
    // Shared pref with Account Name info
    public static final String CONST_SHARED_PREF_ACCOUNT_NAME_INFO =
            "const_shared_pref_account_name_info";

    /* Consts for Intent Actions */
    // Intent action to inform that the config wizard was auto launched by Main Activity
    public static final String CONST_CONFIG_SETUP_WIZARD = "const_config_setup_wizard";
    // Intent action to send IngredientStub id from All Ingredient List to Ingredient Details Activity
    public static final String CONST_INTENT_INGREDIENT_ID = "const_intent_ingredient_id";
    // Intent action to send Category obj from CategoryList to IngredientsByCategory activity
    public static final String CONST_INTENT_CATEGORY = "const_intent_category";

    /* Consts for SavedInstance Bundle */
    // CategoryListActivity - CategoryList Parcelable
    public static final String CONST_BUNDLE_CATEGORY_LIST_PARCELABLE =
            "const_bundle_category_list_parcelable";
    // AllIngredientsListActivity and IngredientsByCategoryActivity
    // - IngredientsStubList Parcelable
    public static final String CONST_BUNDLE_ALL_INGREDIENTS_LIST_PARCELABLE =
            "const_bundle_all_ingredients_list_parcelable";
    // IngredientDetailsActivity - Ingredient Parcelable
    public static final String CONST_BUNDLE_INGREDIENT_PARCELABLE =
            "const_bundle_ingredient_parcelable";
    // IngredientDetailsActivity - Category Parcelable
    public static final String CONST_BUNDLE_CATEGORY_BUNDLE =
            "const_bundle_category_bundle";

    // ContentProvider Authority address
    public static final String AUTHORITY = "br.com.dofukuhara.nutritionalassistant";

    // Function that receives a Cursor and returns a List of Categories
    public static ArrayList<Category> cursorToCategoryArrayList(Cursor cursor) {
        ArrayList<Category> categoryList = new ArrayList<>();
        Category category;
        int categId;
        String categName;

        while (cursor.moveToNext()) {
            categId = cursor.getInt(cursor.getColumnIndex(CategoryContract.CategoryEntry.COLUMN_CATEGORY_ID));
            categName = cursor.getString(cursor.getColumnIndex(CategoryContract.CategoryEntry.COLUMN_CATEGORY_NAME));

            category = new Category(categId, categName);

            categoryList.add(category);
        }

        return categoryList;
    }

    // Function that receives a Cursor and returns a List of IngredientStub
    public static ArrayList<IngredientStub> cursorToIngredientStubList(Cursor cursor) {
        ArrayList<IngredientStub> ingredStubList = new ArrayList<>();
        IngredientStub stub;
        int ingId;
        String ingDesc;
        String ingClass;

        while (cursor.moveToNext()) {
            ingId = cursor.getInt(cursor.getColumnIndex(
                    IngredientStubContract.IngredientStubEntry.COLUMN_INGREDIENT_ID));
            ingDesc = cursor.getString(cursor.getColumnIndex(
                    IngredientStubContract.IngredientStubEntry.COLUMN_INGREDIENT_DESCRIPT));
            ingClass = cursor.getString(cursor.getColumnIndex(
                    IngredientStubContract.IngredientStubEntry.COLUMN_INGREDIENT_CLASSIF));

            stub = new IngredientStub(ingId, ingDesc, ingClass);

            ingredStubList.add(stub);
        }

        return ingredStubList;
    }

    // Function that receives an Ingredient obj and returns a ContentValues obj
    public static ContentValues ingredientToContentValues(Ingredient ingredient) {
        ContentValues retCv = new ContentValues();

        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_ID, ingredient.getId());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_DESCRIPT, ingredient.getDescription());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_CLASSIF, ingredient.getClassification());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_UMITY, ingredient.getUmity());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_ENERGY_KCAL, ingredient.getEnergyKcal());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_ENERGY_KJ, ingredient.getEnergyKj());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_PROTEIN, ingredient.getProtein());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_LIPIDS, ingredient.getLipids());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_CHOLEST, ingredient.getCholesterol());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_CARBO, ingredient.getCarbohydrate());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_FIBER, ingredient.getFoodFiber());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_ASHES, ingredient.getAshes());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_CALCIUM, ingredient.getCalcium());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_MAG, ingredient.getMagnesium());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_NANGA, ingredient.getManganese());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_PHOS, ingredient.getPhosphor());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_IRON, ingredient.getIron());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_SODIUM, ingredient.getSodium());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_POTAS, ingredient.getPotassium());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_COPPER, ingredient.getCopper());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_ZINC, ingredient.getZinc());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_RETI, ingredient.getRetinol());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_RE, ingredient.getRe());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_RAE, ingredient.getRae());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_THIA, ingredient.getThiamine());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_RIBO, ingredient.getRiboflavin());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_PYRI, ingredient.getPyridoxine());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_NIAC, ingredient.getNiacin());
        retCv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_VITAC, ingredient.getVitaminC());

        return retCv;
    }

    public static Ingredient cursorToIngredient(Cursor ingredCursor) {
       return new Ingredient(
               ingredCursor.getInt(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_ID)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_DESCRIPT)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_CLASSIF)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_UMITY)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_ENERGY_KCAL)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_ENERGY_KJ)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_PROTEIN)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_LIPIDS)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_CHOLEST)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_CARBO)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_FIBER)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_ASHES)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_CALCIUM)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_MAG)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_NANGA)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_PHOS)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_IRON)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_SODIUM)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_POTAS)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_COPPER)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_ZINC)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_RETI)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_RE)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_RAE)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_THIA)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_RIBO)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_PYRI)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_NIAC)),
               ingredCursor.getString(ingredCursor.getColumnIndex(
                       IngredientContract.IngredientEntry.COLUMN_INGREDIENT_VITAC))
       );
    }

    // Function that receives a Cursor and return a list of Favorites
    public static ArrayList<Favorite> cursorToFavoriteList(Cursor cursor) {
        ArrayList<Favorite> retList = new ArrayList<>();

        while (cursor.moveToNext()) {
            retList.add(new Favorite(
                    cursor.getInt(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_FAVORITE_ID)),
                    cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_FAVORITE_NAME))
            ));
        }

        return retList;
    }
}
