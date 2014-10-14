package nkichev.wooanna.octopusgameteamwork;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Woo on 14.10.2014 г..
 */
public class SecondPageInstructionsActivity extends Activity {
   private ImageView enemy;
   private TextView enemy_text;
   private String ENEMY_TEXT = "..but be careful, because the big boss wants to smash you!";
    private GestureDetector detector;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_page_instruct_layout);

        enemy = (ImageView)findViewById(R.id.enemy);
        enemy.setImageResource(R.drawable.monster);
        enemy_text = (TextView)findViewById(R.id.enemy_text);
        enemy_text.setText(ENEMY_TEXT);
    }
}
