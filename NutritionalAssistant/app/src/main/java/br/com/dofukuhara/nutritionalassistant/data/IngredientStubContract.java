package br.com.dofukuhara.nutritionalassistant.data;

import android.net.Uri;
import android.provider.BaseColumns;

import br.com.dofukuhara.nutritionalassistant.util.Utils;

/**
 * Created by dofukuhara on 14/03/2018.
 */

public class IngredientStubContract {

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + Utils.AUTHORITY);
    public static final String INGREDIENT_STUB_PATH = "ingredientstub";
    public static final String INGREDIENT_STUB_INGRED_PATH = INGREDIENT_STUB_PATH + "/ing";
    public static final String INGREDIENT_STUB_CLASS_PATH = INGREDIENT_STUB_PATH + "/class";

    public static final class IngredientStubEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(INGREDIENT_STUB_PATH).build();

        public static final String TABLE_NAME = "ingredientstub";

        public static final String COLUMN_INGREDIENT_ID = "ingredientId";
        public static final String COLUMN_INGREDIENT_DESCRIPT = "ingredientDescription";
        public static final String COLUMN_INGREDIENT_CLASSIF = "ingredientClassification";
    }
}
