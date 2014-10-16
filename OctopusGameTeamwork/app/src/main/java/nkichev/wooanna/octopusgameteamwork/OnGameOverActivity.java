package nkichev.wooanna.octopusgameteamwork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import nkichev.wooanna.octopusgameteamwork.HighscoresDB.EntriesDataSource;

/**
 * Created by Woo on 15.10.2014 г..
 */
public class OnGameOverActivity extends Activity implements View.OnClickListener {

    EditText name;
    ImageView btnDone, btnPlayAgain;
    EntriesDataSource dataSource;
    EntriesDataSource dataSourceForReading;
    long score;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_layout);
        dataSource = new EntriesDataSource(this);
        dataSourceForReading = new EntriesDataSource(this);
        btnDone = (ImageView)findViewById(R.id.btn_done);
        btnDone.setImageResource(R.drawable.done);
        btnPlayAgain = (ImageView)findViewById(R.id.btn_play_again);
        btnPlayAgain.setImageResource(R.drawable.play);

        btnPlayAgain.setOnClickListener(this);
        btnDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_done){

            Bundle bundle = this.getIntent().getExtras();
            if(getIntent().hasExtra(GameField.SCORE)){
                this.score = bundle.getLong(GameField.SCORE);
            }

            name = (EditText)findViewById(R.id.username);
            String inputName = name.getText().toString();
            if(inputName.length() <= 2){
                Toast.makeText(this, "Your username must be longer than 2 symbols", Toast.LENGTH_LONG).show();
            }else {
                dataSource.open();
                dataSourceForReading.openForReading();
                id = dataSourceForReading.findEntry(name.getText().toString());
                if (this.id != -1) {
                    dataSource.updateEntry(this.score, this.id);
                    dataSourceForReading.close();

                } else {
                    dataSource.createEntry(name.getText().toString(), this.score);

                }

                Toast.makeText(this, "your score added", Toast.LENGTH_SHORT).show();
            }
        }else if(view.getId() == R.id.btn_play_again){
            Intent in = new Intent(OnGameOverActivity.this, GameField.class);
            startActivity(in);
        }

    }
}
