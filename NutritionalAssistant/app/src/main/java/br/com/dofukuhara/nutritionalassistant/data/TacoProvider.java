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
    // --> Category:
    // --> Ingredient:
    public static final int FAVORITES = 100;
    public static final int FAVORITE_WITH_ID = 101;
    public static final int CATEGORIES = 200;
    public static final int CATEGORY_WITH_ID = 201;
    public static final int INGREDIENTS = 300;
    public static final int INGREDIENT_WITH_ID = 301;

    private TacoDBHelper mTacoDBHelper;

    private static final UriMatcher sUriMater = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(Utils.AUTHORITY, FavoriteContract.FAVORITE_PATH, FAVORITES);
        matcher.addURI(Utils.AUTHORITY, FavoriteContract.FAVORITE_PATH + "/#", FAVORITE_WITH_ID);
        matcher.addURI(Utils.AUTHORITY, CategoryContract.CATEGORY_PATH, CATEGORIES);
        matcher.addURI(Utils.AUTHORITY, CategoryContract.CATEGORY_PATH + "/#", CATEGORY_WITH_ID);
        matcher.addURI(Utils.AUTHORITY, IngredientContract.INGREDIENT_PATH, INGREDIENTS);
        matcher.addURI(Utils.AUTHORITY, IngredientContract.INGREDIENT_PATH + "/#", INGREDIENT_WITH_ID);

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

        switch (sUriMater.match(uri)) {
            case FAVORITES:
                retCursor = db.query(FavoriteContract.FavoriteEntry.TABLE_NAME,
                        projections, selection, selectionArgs,
                        null, null, sortOrder);

                break;
            case FAVORITE_WITH_ID:
                String id = uri.getPathSegments().get(1);

                retCursor = db.query(FavoriteContract.FavoriteEntry.TABLE_NAME, projections,
                        FavoriteContract.FavoriteEntry._ID + "=?", new String[] {id},
                        null, null, sortOrder);

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
                return "vnd.android.cursor.item/vnd.br.com.dofukuhara.nutritionalassistant.favorite";

            case FAVORITE_WITH_ID:
                return "vnd.android.cursor.dir/vnd.br.com.dofukuhara.nutritionalassistant.favorite";

            case CATEGORIES:
                return "vnd.android.cursor.item/vnd.br.com.dofukuhara.nutritionalassistant.category";

            case CATEGORY_WITH_ID:
                return "vnd.android.cursor.dir/vnd.br.com.dofukuhara.nutritionalassistant.category";

            case INGREDIENTS:
                return "vnd.android.cursor.item/vnd.br.com.dofukuhara.nutritionalassistant.ingredient";

            case INGREDIENT_WITH_ID:
                return "vnd.android.cursor.dir/vnd.br.com.dofukuhara.nutritionalassistant.ingredient";

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
                    throw new SQLException("Failed to insert Favorite into: " + uri);
                } else {
                    retUri = ContentUris.withAppendedId(FavoriteContract.FavoriteEntry.CONTENT_URI, newFavId);
                }
                break;

            case FAVORITE_WITH_ID:

                // TODO: Probably this won't be used!!!
            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return retUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String where, @Nullable String[] whereArgs) {
        final SQLiteDatabase db = mTacoDBHelper.getWritableDatabase();

        int retDeletedId;

        switch (sUriMater.match(uri)) {
            case FAVORITE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                retDeletedId = db.delete(FavoriteContract.FavoriteEntry.TABLE_NAME,
                        "_id=?", new String[]{id});
                break;

            case FAVORITES:
                retDeletedId = db.delete(FavoriteContract.FavoriteEntry.TABLE_NAME,
                        where, whereArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }

        if (retDeletedId != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return retDeletedId;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        // This provider won' support UPDATE operation by now
        return 0;
    }
}
