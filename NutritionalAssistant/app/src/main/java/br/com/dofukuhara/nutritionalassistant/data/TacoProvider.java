package br.com.dofukuhara.nutritionalassistant.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import br.com.dofukuhara.nutritionalassistant.util.Utils;

/**
 * Created by dofukuhara on 19/02/2018.
 */

public class TacoProvider extends ContentProvider {

    // TODO: Implement ContentProvider for:
    // --> Favorite: OK
    // --> Category: OK
    // --> IngredientStub: OK
    // --> Ingredient: OK
    // --> Recipe:
    public static final int FAVORITES = 100;
    public static final int FAVORITE_WITH_ID = 101;
    public static final int CATEGORIES = 200;
    public static final int CATEGORY_WITH_ROW_ID = 201;
    public static final int CATEGORY_WITH_CATEGORY_ID = 202;
    public static final int INGREDIENTS_STUB = 300;
    public static final int INGREDIENT_STUBS_WITH_INGREDIENT_ID = 301;
    public static final int INGREDIENT_STUBS_WITH_CLASSIFICATION_ID = 302;
    public static final int INGREDIENTS = 400;
    public static final int INGREDIENT_WITH_INGREDIENT_ID = 401;
    public static final int RECIPES = 500;
    public static final int RECIPES_WITH_ID = 501;

    private TacoDBHelper mTacoDBHelper;

    private static final UriMatcher sUriMater = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(Utils.AUTHORITY, FavoriteContract.FAVORITE_PATH, FAVORITES);
        matcher.addURI(Utils.AUTHORITY, FavoriteContract.FAVORITE_PATH + "/#", FAVORITE_WITH_ID);
        matcher.addURI(Utils.AUTHORITY, CategoryContract.CATEGORY_PATH, CATEGORIES);
        matcher.addURI(Utils.AUTHORITY, CategoryContract.CATEGORY_PATH + "/#", CATEGORY_WITH_CATEGORY_ID);
        matcher.addURI(Utils.AUTHORITY, CategoryContract.CATEGORY_ROW_PATH + "/#", CATEGORY_WITH_ROW_ID);
        matcher.addURI(Utils.AUTHORITY, IngredientStubContract.INGREDIENT_STUB_PATH, INGREDIENTS_STUB);
        matcher.addURI(Utils.AUTHORITY, IngredientStubContract.INGREDIENT_STUB_INGRED_PATH + "/#",
                INGREDIENT_STUBS_WITH_INGREDIENT_ID);
        matcher.addURI(Utils.AUTHORITY, IngredientStubContract.INGREDIENT_STUB_CLASS_PATH + "/#",
                INGREDIENT_STUBS_WITH_CLASSIFICATION_ID);
        matcher.addURI(Utils.AUTHORITY, IngredientContract.INGREDIENT_PATH, INGREDIENTS);
        matcher.addURI(Utils.AUTHORITY, IngredientContract.INGREDIENT_PATH + "/#",
                INGREDIENT_WITH_INGREDIENT_ID);
        matcher.addURI(Utils.AUTHORITY, RecipeContract.RECIPE_PATH, RECIPES);
        matcher.addURI(Utils.AUTHORITY, RecipeContract.RECIPE_PATH + "/#", RECIPES_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mTacoDBHelper = new TacoDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projections, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mTacoDBHelper.getReadableDatabase();

        Cursor retCursor;
        String id;

        switch (sUriMater.match(uri)) {
            case FAVORITES:
                retCursor = db.query(FavoriteContract.FavoriteEntry.TABLE_NAME,
                        projections, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case FAVORITE_WITH_ID:
                id = uri.getPathSegments().get(1);

                retCursor = db.query(FavoriteContract.FavoriteEntry.TABLE_NAME, projections,
                        FavoriteContract.FavoriteEntry._ID + "=?", new String[] {id},
                        null, null, sortOrder);
                break;

            case CATEGORIES:
                retCursor = db.query(CategoryContract.CategoryEntry.TABLE_NAME,
                        projections, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case CATEGORY_WITH_ROW_ID:
                id = uri.getPathSegments().get(2);
                retCursor = db.query(CategoryContract.CategoryEntry.TABLE_NAME, projections,
                        CategoryContract.CategoryEntry._ID + "=?", new String[] {id},
                        null, null, sortOrder);
                break;

            case CATEGORY_WITH_CATEGORY_ID:
                id = uri.getPathSegments().get(1);

                retCursor = db.query(CategoryContract.CategoryEntry.TABLE_NAME, projections,
                        CategoryContract.CategoryEntry.COLUMN_CATEGORY_ID + "=?", new String[] {id},
                        null, null, sortOrder);
                break;

            case INGREDIENTS_STUB:
                retCursor = db.query(IngredientStubContract.IngredientStubEntry.TABLE_NAME,
                        projections, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case INGREDIENT_STUBS_WITH_INGREDIENT_ID:
                id = uri.getPathSegments().get(2);

                retCursor = db.query(IngredientStubContract.IngredientStubEntry.TABLE_NAME, projections,
                        IngredientStubContract.IngredientStubEntry.COLUMN_INGREDIENT_ID + "=?",
                        new String[] {id}, null, null, sortOrder);
                break;

            case INGREDIENT_STUBS_WITH_CLASSIFICATION_ID:
                id = uri.getPathSegments().get(2);

                retCursor = db.query(IngredientStubContract.IngredientStubEntry.TABLE_NAME, projections,
                        IngredientStubContract.IngredientStubEntry.COLUMN_INGREDIENT_CLASSIF + "=?",
                        new String[] {id}, null, null, sortOrder);
                break;

            case INGREDIENTS:
                retCursor = db.query(IngredientContract.IngredientEntry.TABLE_NAME,
                        projections, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case INGREDIENT_WITH_INGREDIENT_ID:
                id = uri.getPathSegments().get(1);

                retCursor = db.query(IngredientContract.IngredientEntry.TABLE_NAME, projections,
                        IngredientContract.IngredientEntry.COLUMN_INGREDIENT_ID + "=?",
                        new String[] {id}, null, null, sortOrder);
                break;

            case RECIPES:
                retCursor = db.query(RecipeContract.RecipeEntry.TABLE_NAME,
                        projections, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case RECIPES_WITH_ID:
                id = uri.getPathSegments().get(1);

                retCursor = db.query(RecipeContract.RecipeEntry.TABLE_NAME, projections,
                        RecipeContract.RecipeEntry._ID + "=?",
                        new String[] {id}, null, null, sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMater.match(uri)) {
            case FAVORITES:
                return "vnd.android.cursor.dir/vnd.br.com.dofukuhara.nutritionalassistant.favorite";

            case FAVORITE_WITH_ID:
                return "vnd.android.cursor.item/vnd.br.com.dofukuhara.nutritionalassistant.favorite";

            case CATEGORIES:
                return "vnd.android.cursor.dir/vnd.br.com.dofukuhara.nutritionalassistant.category";

            case CATEGORY_WITH_ROW_ID:
                return "vnd.android.cursor.item/vnd.br.com.dofukuhara.nutritionalassistant.category/row";

            case CATEGORY_WITH_CATEGORY_ID:
                return "vnd.android.cursor.item/vnd.br.com.dofukuhara.nutritionalassistant.category";

            case INGREDIENTS_STUB:
                return "vnd.android.cursor.dir/vnd.br.com.dofukuhara.nutritionalassistant.ingredientstub";

            case INGREDIENT_STUBS_WITH_INGREDIENT_ID:
                return "vnd.android.cursor.item/vnd.br.com.dofukuhara.nutritionalassistant.ingredientstub/ing";

            case INGREDIENT_STUBS_WITH_CLASSIFICATION_ID:
                return "vnd.android.cursor.item/vnd.br.com.dofukuhara.nutritionalassistant.ingredientstub/class";

            case INGREDIENTS:
                return "vnd.android.cursor.dir/vnd.br.com.dofukuhara.nutritionalassistant.ingredient";

            case INGREDIENT_WITH_INGREDIENT_ID:
                return "vnd.android.cursor.item/vnd.br.com.dofukuhara.nutritionalassistant.ingredient";

            case RECIPES:
                return "vnd.android.cursor.dir/vnd.br.com.dofukuhara.nutritionalassistant.recipe";

            case RECIPES_WITH_ID:
                return "vnd.android.cursor.item/vnd.br.com.dofukuhara.nutritionalassistant.recipe";

            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = mTacoDBHelper.getWritableDatabase();

        Uri retUri;

        switch (sUriMater.match(uri)) {
            case FAVORITES:
                long newFavId = db.insert(FavoriteContract.FavoriteEntry.TABLE_NAME,
                        null, contentValues);
                if (newFavId < 0) {
                    return null;
                } else {
                    retUri = ContentUris.withAppendedId(FavoriteContract.FavoriteEntry.CONTENT_URI, newFavId);
                }
                break;

            case CATEGORIES:
                long newCategId = db.insert(CategoryContract.CategoryEntry.TABLE_NAME,
                        null, contentValues);
                if (newCategId < 0) {
                    throw new SQLException("Failed to insert Category into: " + uri);
                } else {
                    retUri = ContentUris.withAppendedId(CategoryContract.CategoryEntry.CONTENT_URI, newCategId);
                }
                break;

            case INGREDIENTS_STUB:
                long newIngredStubId = db.insert(IngredientStubContract.IngredientStubEntry.TABLE_NAME,
                        null, contentValues);
                if (newIngredStubId < 0) {
                    throw new SQLException("Failed to insert IngredientStub into: " + uri);
                } else {
                    retUri = ContentUris.withAppendedId(
                            IngredientStubContract.IngredientStubEntry.CONTENT_URI, newIngredStubId);
                }
                break;

            case INGREDIENTS:
                long newIngredId = db.insert(IngredientContract.IngredientEntry.TABLE_NAME,
                        null, contentValues);
                if (newIngredId < 0) {
                    throw new SQLException("Failed to insert Ingredient into: " + uri);
                } else {
                    retUri = ContentUris.withAppendedId(
                            IngredientContract.IngredientEntry.CONTENT_URI, newIngredId);
                }
                break;

            case RECIPES:
                long newRecipeId = db.insert(RecipeContract.RecipeEntry.TABLE_NAME,
                        null, contentValues);
                if (newRecipeId < 0) {
                    throw new SQLException("Failed to insert Recipe into " + uri);
                } else {
                    retUri = ContentUris.withAppendedId(
                            RecipeContract.RecipeEntry.CONTENT_URI, newRecipeId);
                }
                break;

            case FAVORITE_WITH_ID:
            case CATEGORY_WITH_ROW_ID:
            case CATEGORY_WITH_CATEGORY_ID:
            case INGREDIENT_STUBS_WITH_INGREDIENT_ID:
            case INGREDIENT_STUBS_WITH_CLASSIFICATION_ID:
            case INGREDIENT_WITH_INGREDIENT_ID:
            case RECIPES_WITH_ID:

            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return retUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String where, @Nullable String[] whereArgs) {
        final SQLiteDatabase db = mTacoDBHelper.getWritableDatabase();

        int retNumberDeleted;
        String id;

        switch (sUriMater.match(uri)) {
            case FAVORITE_WITH_ID:
                id = uri.getPathSegments().get(1);
                retNumberDeleted = db.delete(FavoriteContract.FavoriteEntry.TABLE_NAME,
                        FavoriteContract.FavoriteEntry.COLUMN_FAVORITE_ID + "=?",
                        new String[]{id});
                break;

            case FAVORITES:
                retNumberDeleted = db.delete(FavoriteContract.FavoriteEntry.TABLE_NAME,
                        where, whereArgs);
                break;

            case RECIPES_WITH_ID:
                id = uri.getPathSegments().get(1);
                retNumberDeleted = db.delete(RecipeContract.RecipeEntry.TABLE_NAME,
                        RecipeContract.RecipeEntry._ID + "=?",
                        new String[] {id});
                break;

            // TODO: Check if Category DELETE operation should be implemented or not
            case CATEGORIES:
            case CATEGORY_WITH_ROW_ID:
            case CATEGORY_WITH_CATEGORY_ID:
            // TODO: Check if IngredientStub DELETE operation should be implemented or not
            case INGREDIENTS_STUB:
            case INGREDIENT_STUBS_WITH_INGREDIENT_ID:
            case INGREDIENT_STUBS_WITH_CLASSIFICATION_ID:
            // TODO: Check if Ingredient DELETE operation should be implemented or not
            case INGREDIENTS:
            case INGREDIENT_WITH_INGREDIENT_ID:
            // TODO: Check if RECIPES DELETE operation should be implemented or not
            case RECIPES:

            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }

        if (retNumberDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return retNumberDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues,
                      @Nullable String whereClause, @Nullable String[] whereArgs) {
        // This provider won' support UPDATE operation by now
        //return 0;

        final SQLiteDatabase db = mTacoDBHelper.getWritableDatabase();

        int retNumberUpdated = 0;
        String id;

        switch (sUriMater.match(uri)) {
            case RECIPES:
                db.update(RecipeContract.RecipeEntry.TABLE_NAME,
                        contentValues, whereClause, whereArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);

        }

        return retNumberUpdated;
    }
}
