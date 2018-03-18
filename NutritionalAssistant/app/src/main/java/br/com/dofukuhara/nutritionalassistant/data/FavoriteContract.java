package br.com.dofukuhara.nutritionalassistant.data;

import android.net.Uri;
import android.provider.BaseColumns;

import br.com.dofukuhara.nutritionalassistant.util.Utils;

/**
 * Created by dofukuhara on 12/03/2018.
 */

public class FavoriteContract {
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + Utils.AUTHORITY);
    public static final String FAVORITE_PATH = "favorite";

    public static final class FavoriteEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(FAVORITE_PATH).build();

        public static final String TABLE_NAME = "favorite";

        public static final String COLUMN_FAVORITE_ID = "favoriteId";
        public static final String COLUMN_FAVORITE_NAME = "favoriteName";
    }
}
