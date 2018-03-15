package br.com.dofukuhara.nutritionalassistant.data;

import android.net.Uri;
import android.provider.BaseColumns;

import br.com.dofukuhara.nutritionalassistant.util.Utils;

/**
 * Created by dofukuhara on 19/02/2018.
 */

public class CategoryContract {

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + Utils.AUTHORITY);
    public static final String CATEGORY_PATH = "category";
    public static final String CATEGORY_ROW_PATH = CATEGORY_PATH + "/row";

    public static final class CategoryEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(CATEGORY_PATH).build();

        public static final String TABLE_NAME = "category";

        public static final String COLUMN_CATEGORY_ID = "categoryId";
        public static final String COLUMN_CATEGORY_NAME = "categoryName";
        public static final String COLUMN_UPDATED_DATE = "categoryUpdatedDate";
    }
}
