package br.com.dofukuhara.nutritionalassistant.data;

import android.net.Uri;
import android.provider.BaseColumns;

import br.com.dofukuhara.nutritionalassistant.util.Utils;

/**
 * Created by dofukuhara on 19/03/2018.
 */

public class RecipeContract {

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + Utils.AUTHORITY);
    public static final String RECIPE_PATH = "recipe";

    public static final class RecipeEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(RECIPE_PATH).build();

        public static final String TABLE_NAME = "recipe";

        public static final String COLUMN_RECIPE_NAME = "recipeName";
        public static final String COLUMN_RECIPE_INGREDIENTS = "recipeIngredients";
    }

}
