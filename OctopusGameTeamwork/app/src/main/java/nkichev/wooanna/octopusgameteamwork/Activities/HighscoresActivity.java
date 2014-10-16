package nkichev.wooanna.octopusgameteamwork.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import nkichev.wooanna.octopusgameteamwork.HighscoresDB.EntriesDataSource;
import nkichev.wooanna.octopusgameteamwork.HighscoresDB.Entry;
import nkichev.wooanna.octopusgameteamwork.R;

public class HighscoresActivity extends Activity {

    private EntriesDataSource datasource;
    private ArrayList<Entry> entries;
    private ListView list;
    private TextView highscores;
    private TextView name;
    private TextView score;
    private TextView entry_name;
    private TextView entry_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscores_layout);
        Typeface font = Typeface.createFromAsset(getAssets(), "BEERNOTE.ttf");
        highscores = (TextView)findViewById(R.id.highscores);
        highscores.setTypeface(font);
        name = (TextView)findViewById(R.id.tvName);
        name.setTypeface(font);
        score = (TextView)findViewById(R.id.tvScore);
        score.setTypeface(font);

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
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_toMain) {
            Intent in = new Intent(HighscoresActivity.this, MenuActivity.class);
            startActivity(in);
        }
        return super.onOptionsItemSelected(item);
    }

}
