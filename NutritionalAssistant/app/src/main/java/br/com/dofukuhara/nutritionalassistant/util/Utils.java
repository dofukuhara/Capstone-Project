package br.com.dofukuhara.nutritionalassistant.util;

import br.com.dofukuhara.nutritionalassistant.BuildConfig;

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

    /* Consts for SavedInstance Bundle */
    // CategoryListActivity - CategoryList Parcelable
    public static final String CONST_BUNDLE_CATEGORY_LIST_PARCELABLE =
            "const_bundle_category_list_parcelable";
    // AllIngredientsListActivity - IngredientsList Parcelable
    public static final String CONST_BUNDLE_ALL_INGREDIENTS_LIST_PARCELABLE =
            "const_bundle_all_ingredients_list_parcelable";
    // IngredientDetailsActivity - Ingredient Parcelable
    public static final String CONST_BUNDLE_INGREDIENT_PARCELABLE =
            "const_bundle_ingredient_parcelable";

    // ContentProvider Authority address
    public static final String AUTHORITY = "br.com.dofukuhara.nutritionalassistant";
}
