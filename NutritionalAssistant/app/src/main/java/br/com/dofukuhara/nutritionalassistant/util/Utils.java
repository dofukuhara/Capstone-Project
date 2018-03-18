package br.com.dofukuhara.nutritionalassistant.util;

import android.database.Cursor;

import java.util.ArrayList;

import br.com.dofukuhara.nutritionalassistant.data.CategoryContract;
import br.com.dofukuhara.nutritionalassistant.data.IngredientStubContract;
import br.com.dofukuhara.nutritionalassistant.model.Category;
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
}
