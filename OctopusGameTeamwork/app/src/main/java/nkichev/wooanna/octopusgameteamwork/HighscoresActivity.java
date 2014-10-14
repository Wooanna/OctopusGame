package nkichev.wooanna.octopusgameteamwork;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import nkichev.wooanna.octopusgameteamwork.HighscoresDB.EntriesDataSource;
import nkichev.wooanna.octopusgameteamwork.HighscoresDB.Entry;

public class HighscoresActivity extends Activity {

    private EntriesDataSource datasource;
    private ArrayList<Entry> entries;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscores_layout);

        datasource = new EntriesDataSource(this);
        datasource.open();
        datasource.createEntry("Test3", 24);
        /*datasource.createEntry("Test2", 9);*/
        entries = datasource.getAllEntries();

        list = (ListView) findViewById(R.id.highscoresList);
        EntryAdapter adapter = new EntryAdapter(this, R.layout.highscores_row_entry, entries);

        list.setAdapter(adapter);

    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.highscores, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
