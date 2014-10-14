package nkichev.wooanna.octopusgameteamwork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import nkichev.wooanna.octopusgameteamwork.GameAudio.Assets;
import nkichev.wooanna.octopusgameteamwork.GameAudio.GameAudio;


public class MenuActivity extends Activity implements View.OnClickListener{
    ImageView play;
    ImageView options;
    ImageView instructions;
    GameAudio gameAudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        play = (ImageView)findViewById(R.id.play);
        options = (ImageView)findViewById(R.id.options);
        instructions = (ImageView)findViewById(R.id.instructions);
        play.setImageResource(R.drawable.start_btn);
        options.setImageResource(R.drawable.options_btn);
        instructions.setImageResource(R.drawable.options_btn);
        play.setOnClickListener(this);
        instructions.setOnClickListener(this);

        gameAudio = new GameAudio(this);
        //Assets.song = gameAudio.newMusic("Plodchetata.mp3");
       // Assets.song.play();
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
            Intent in = new Intent(MenuActivity.this , GameField.class);
            startActivity(in);
        }else if(view.getId() == findViewById(R.id.instructions).getId()){
            Intent in = new Intent(MenuActivity.this , InstructionsActivity.class);
            startActivity(in);
        }
    }
}
