package windowmanager.project.angel.com.windowmanagerexample;

import android.app.Application;

/**
 * @author suzhuning
 * @date 2017/2/8.
 * Description:
 */
public class MyApplication extends Application {

    private SQLiteHelper dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        dbHelper = new SQLiteHelper(this);
    }

    public SQLiteHelper getDbHelper() {
        return dbHelper;
    }
}
