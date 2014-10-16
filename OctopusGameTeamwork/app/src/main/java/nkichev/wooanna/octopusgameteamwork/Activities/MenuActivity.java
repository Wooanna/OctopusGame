package nkichev.wooanna.octopusgameteamwork.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import nkichev.wooanna.octopusgameteamwork.GameAudio.BackgroundMusicService;
import nkichev.wooanna.octopusgameteamwork.GameAudio.GameAudio;
import nkichev.wooanna.octopusgameteamwork.R;


public class MenuActivity extends Activity implements View.OnClickListener{
    ImageView play;
    ImageView options;
    ImageView instructions;
    GameAudio gameAudio;
    Intent playbackServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        play = (ImageView)findViewById(R.id.play);
        options = (ImageView)findViewById(R.id.options);
        instructions = (ImageView)findViewById(R.id.instructions);
        play.setImageResource(R.drawable.play);
        options.setImageResource(R.drawable.rules);
        instructions.setImageResource(R.drawable.scores);
        play.setOnClickListener(this);
        instructions.setOnClickListener(this);

        //just for testing highscores activity
        options.setOnClickListener(this);

        gameAudio = new GameAudio(this);


        // background music service
        playbackServiceIntent = new Intent(this, BackgroundMusicService.class);
        startService(playbackServiceIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onStop() {
        super.onStop();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == findViewById(R.id.play).getId()) {
            if (InternetChecker.checkConn(MenuActivity.this)){
                Intent in = new Intent(MenuActivity.this , GameField.class);
                startActivity(in);
            }else {
                Toast.makeText(MenuActivity.this, "No connection available",
                        Toast.LENGTH_SHORT).show();
            }
        } else if(view.getId() == findViewById(R.id.instructions).getId()){
            Intent in = new Intent(MenuActivity.this , HighscoresActivity.class);
            startActivity(in);
        } else if(view.getId() == findViewById(R.id.options).getId()) {
            Intent in = new Intent(MenuActivity.this , InstructionsActivity.class);
            startActivity(in);
        }
    }
}
