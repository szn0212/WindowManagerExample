package windowmanager.project.angel.com.windowmanagerexample;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

/**
 * @author suzhuning
 * @date 2017/2/8.
 * Description:
 */
public class SearchableActivity extends ListActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    private void doMySearch(String query) {
        SQLiteHelper sqLiteHelper = ((MyApplication)getApplication()).getDbHelper();
        Cursor cursor = sqLiteHelper.getReadableDatabase().rawQuery("SELECT " + DatabaseConstants.COL_LANG_ID + ", " +
                DatabaseConstants.COL_LANG_NAME + " FROM " + DatabaseConstants.TABLE_LANG +
                " WHERE upper(" + DatabaseConstants.COL_LANG_NAME + ") like '%" + query.toUpperCase() + "%'", null);
        setListAdapter(new SimpleCursorAdapter(this, R.layout.layout_serach_item, cursor,
                new String[] {DatabaseConstants.COL_LANG_NAME }, new int[]{R.id.search_item_text}));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }
}
