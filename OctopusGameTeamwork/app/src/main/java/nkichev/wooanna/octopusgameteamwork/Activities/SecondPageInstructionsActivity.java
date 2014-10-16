package nkichev.wooanna.octopusgameteamwork.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nkichev.wooanna.octopusgameteamwork.R;

/**
 * Created by Woo on 14.10.2014 г..
 */
public class SecondPageInstructionsActivity extends Activity implements View.OnClickListener{
   private ImageView enemy;
   private TextView enemy_text;
    private ImageView play_again;
   private String ENEMY_TEXT = "..but be careful, because the big boss wants to smash you!";
    private GestureDetector detector;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_page_instruct_layout);
        Typeface font = Typeface.createFromAsset(getAssets(), "BEERNOTE.ttf");

        enemy = (ImageView)findViewById(R.id.enemy);
        enemy.setImageResource(R.drawable.monster);
        enemy_text = (TextView)findViewById(R.id.enemy_text);
        enemy_text.setText(ENEMY_TEXT);
        enemy_text.setTypeface(font);

        play_again = (ImageView)findViewById(R.id.play_again);
        play_again.setImageResource(R.drawable.play);
        play_again.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.play_again){
            Intent i = new Intent(SecondPageInstructionsActivity.this, GameField.class);
            startActivity(i);
        }
    }
}
