package windowmanager.project.angel.com.windowmanagerexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author suzhuning
 * @date 2017/2/8.
 * Description:
 */
public class SQLiteHelper extends SQLiteOpenHelper implements DatabaseConstants {

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_LANG + "(" + COL_LANG_ID
                + " INTEGER PRIMARY KEY NOT NULL, " + " " + COL_LANG_NAME
                + " VARCHAR(50) NOT NULL);");
        insertLanguage(db, "Java");
        insertLanguage(db, "Perl");
        insertLanguage(db, "Python");
        insertLanguage(db, "Ruby");
        insertLanguage(db, "Scala");
        insertLanguage(db, "C");
        insertLanguage(db, "C++");
    }

    private void insertLanguage(SQLiteDatabase db, String language) {
        db.execSQL("INSERT INTO " + TABLE_LANG + " (" + COL_LANG_NAME
                + ") VALUES ('" + language + "');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
