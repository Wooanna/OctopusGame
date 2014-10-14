package nkichev.wooanna.octopusgameteamwork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Woo on 14.10.2014 г..
 */
public class ActivityPoused extends Activity implements View.OnClickListener {

    ImageView poused;
    Intent intent;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paused_layout);

        poused = (ImageView)findViewById(R.id.resume);
        poused.setImageResource(R.drawable.resume);
        poused.setOnClickListener(this);

        }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.resume){
            intent = new Intent(ActivityPoused.this, GameField.class);
            startActivity(intent);
        }
    }
}
