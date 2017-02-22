package windowmanager.project.angel.com.windowmanagerexample;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * @author suzhuning
 * @date 2017/2/8.
 * Description:
 */
public class LanguageContentProvider extends ContentProvider{

    private static final String AUTHORITY = "windowmanager.project.angel.com.windowmanagerexample.LanguageContentProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + DatabaseConstants.TABLE_LANG);

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(DatabaseConstants.TABLE_LANG);
        String orderBy = DatabaseConstants.COL_LANG_NAME + " asc";
        Cursor cursor = qb.query(getDbHelper().getReadableDatabase(),
                new String[]{DatabaseConstants.COL_LANG_ID,
                        DatabaseConstants.COL_LANG_NAME}, null,
                null, null, null, orderBy);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return "vnd.android.cursor.dir/vnd.windowmanager.project.angel.com.windowmanagerexample.LanguageContentProvider";
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    public SQLiteOpenHelper getDbHelper() {
        return ((MyApplication)getContext().getApplicationContext()).getDbHelper();
    }
}
