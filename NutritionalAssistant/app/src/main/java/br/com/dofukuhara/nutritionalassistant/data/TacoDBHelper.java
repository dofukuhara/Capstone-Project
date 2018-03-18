package br.com.dofukuhara.nutritionalassistant.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dofukuhara on 19/02/2018.
 */

public class TacoDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "taco.db";
    private static final int VERSION = 1;

    TacoDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String createFavoriteTable = "CREATE TABLE " + FavoriteContract.FavoriteEntry.TABLE_NAME + " (" +
                FavoriteContract.FavoriteEntry._ID + " INTEGER PRIMARY KEY, " +
                FavoriteContract.FavoriteEntry.COLUMN_FAVORITE_ID + " INTEGER NOT NULL, " +
                FavoriteContract.FavoriteEntry.COLUMN_FAVORITE_NAME + " TEXT);";
        db.execSQL(createFavoriteTable);

        final String createCategoryTable = "CREATE TABLE " + CategoryContract.CategoryEntry.TABLE_NAME + " (" +
                CategoryContract.CategoryEntry._ID + " INTEGER PRIMARY KEY, " +
                CategoryContract.CategoryEntry.COLUMN_CATEGORY_ID + " INTEGER NOT NULL, " +
                CategoryContract.CategoryEntry.COLUMN_CATEGORY_NAME + " TEXT, " +
                CategoryContract.CategoryEntry.COLUMN_UPDATED_DATE + ");";

        db.execSQL(createCategoryTable);

        final String createIngredientStubTable = "CREATE TABLE " +
                IngredientStubContract.IngredientStubEntry.TABLE_NAME + " (" +
                IngredientStubContract.IngredientStubEntry._ID + " INTEGER PRIMARY KEY, " +
                IngredientStubContract.IngredientStubEntry.COLUMN_INGREDIENT_ID + " INTEGER NOT NULL, " +
                IngredientStubContract.IngredientStubEntry.COLUMN_INGREDIENT_DESCRIPT + " TEXT, " +
                IngredientStubContract.IngredientStubEntry.COLUMN_INGREDIENT_CLASSIF + " TEXT);";

        db.execSQL(createIngredientStubTable);

        // TODO: Create Recipe Table

        final String createIngredientTable = "CREATE TABLE " + IngredientContract.IngredientEntry.TABLE_NAME + " (" +
                IngredientContract.IngredientEntry._ID + " INTEGER PRIMARY KEY, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_ID + " INTEGER NOT NULL, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_DESCRIPT + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_CLASSIF + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_UMITY + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_ENERGY_KCAL + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_ENERGY_KJ + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_PROTEIN + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_LIPIDS + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_CHOLEST + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_CARBO + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_FIBER + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_ASHES + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_CALCIUM + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_MAG + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_NANGA + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_PHOS + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_IRON + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_SODIUM + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_POTAS + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_COPPER + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_ZINC + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_RETI + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_RE + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_RAE + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_THIA + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_RIBO + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_PYRI + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_NIAC + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_INGREDIENT_VITAC + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_UPDATED_DATE + ");";

        db.execSQL(createIngredientTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoryContract.CategoryEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + IngredientContract.IngredientEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteContract.FavoriteEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + IngredientStubContract.IngredientStubEntry.TABLE_NAME);

        onCreate(db);
    }
}
