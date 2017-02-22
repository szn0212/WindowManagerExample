package windowmanager.project.angel.com.windowmanagerexample;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.widget.SearchView;

/**
 * @author suzhuning
 * @date 2017/2/8.
 * Description:
 */
public class SearchListActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID = 42;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search);

        adapter = new SimpleCursorAdapter(this, R.layout.layout_serach_item, null,
                new String[]{DatabaseConstants.COL_LANG_NAME}, new int[]{R.id.search_item_text},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        setListAdapter(adapter);
        getLoaderManager().initLoader(LOADER_ID, null, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public void openOptionsMenu() {
        super.openOptionsMenu();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        if(id != LOADER_ID){
            return null;
        }
        return new CursorLoader(this, LanguageContentProvider.CONTENT_URI, new String[]{DatabaseConstants.COL_LANG_ID,
                DatabaseConstants.COL_LANG_NAME}, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
