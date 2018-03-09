package br.com.dofukuhara.nutritionalassistant.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by dofukuhara on 19/02/2018.
 */

public class IngredientContract {

    public static final String AUTORITHY = "br.com.dofukuhara.nutritionalassistant";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTORITHY);
    public static final String INGREDIENT_PATH = "ingredient";

    public static final class IngredientEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(INGREDIENT_PATH).build();

        public static final String TABLE_NAME = "ingredient";

        public static final String COLUMN_INGREDIENT_ID = "ingredientId";
        public static final String COLUMN_INGREDIENT_DESCRIPT = "ingredientDescription";
        public static final String COLUMN_INGREDIENT_CLASSIF = "ingredientClassification";
        public static final String COLUMN_INGREDIENT_UMITY = "ingredientUmity";
        public static final String COLUMN_INGREDIENT_ENERGY = "ingredientEnergy";
        public static final String COLUMN_INGREDIENT_PROTEIN = "ingredientProtein";
        public static final String COLUMN_INGREDIENT_LIPIDS = "ingredientLipids";
        public static final String COLUMN_INGREDIENT_CHOLEST = "ingredientCholesterol";
        public static final String COLUMN_INGREDIENT_CARBO = "ingredientCarbohydrate";
        public static final String COLUMN_INGREDIENT_FIBER = "ingredientFoodFiber";
        public static final String COLUMN_INGREDIENT_ASHES = "ingredientAshes";
        public static final String COLUMN_INGREDIENT_CALCIUM = "ingredientCalcium";
        public static final String COLUMN_INGREDIENT_MAG = "ingredientMagnesium";
        public static final String COLUMN_INGREDIENT_NANGA = "ingredientManganese";
        public static final String COLUMN_INGREDIENT_PHOS = "ingredientPhosphor";
        public static final String COLUMN_INGREDIENT_IRON = "ingredientIron";
        public static final String COLUMN_INGREDIENT_SODIUM = "ingredientSodium";
        public static final String COLUMN_INGREDIENT_POTAS = "ingredientPotassium";
        public static final String COLUMN_INGREDIENT_COPPER = "ingredientCopper";
        public static final String COLUMN_INGREDIENT_ZINC = "ingredientZinc";
        public static final String COLUMN_INGREDIENT_RETI = "ingredientRetinol";
        public static final String COLUMN_INGREDIENT_RE = "ingredientRe";
        public static final String COLUMN_INGREDIENT_RAE = "ingredientRae";
        public static final String COLUMN_INGREDIENT_THIA = "ingredientThiamine";
        public static final String COLUMN_INGREDIENT_RIBO = "ingredientRiboflavin";
        public static final String COLUMN_INGREDIENT_PYRI = "ingredientPyridoxine";
        public static final String COLUMN_INGREDIENT_NIAC = "ingredientNiacin";
        public static final String COLUMN_INGREDIENT_VITAC = "ingredientVitaminC";
        public static final String COLUMN_UPDATED_DATE = "ingredientUpdatedDate";
    }
}
