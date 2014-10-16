package nkichev.wooanna.octopusgameteamwork.Activities;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import nkichev.wooanna.octopusgameteamwork.HighscoresDB.EntriesDataSource;
import nkichev.wooanna.octopusgameteamwork.R;

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
    Notification newBestScoreNotification;

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
    public void onBackPressed() {
        Intent i = new Intent(OnGameOverActivity.this, MenuActivity.class);
        startActivity(i);

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

                long currentBestScore = dataSource.getBestScore();
                if (currentBestScore != -1 && currentBestScore < score) {
                    Toast.makeText(this, "Congratulations, you achieved a new best score!",
                            Toast.LENGTH_SHORT).show();
                    newBestScoreNotification = new Notification.Builder(this)
                            .setContentTitle("New best score : "  + String.valueOf(score))
                            .setContentText("You made it!")
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setAutoCancel(true).build();

                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                    notificationManager.notify(0, newBestScoreNotification);
                }

                if (this.id != -1) {
                    dataSource.updateEntry(this.score, this.id);
                    dataSourceForReading.close();

                } else {
                    dataSource.createEntry(name.getText().toString(), this.score);

                }

                Toast.makeText(this, "your score added", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(OnGameOverActivity.this, HighscoresActivity.class);
                startActivity(in);
            }
        }
        if(view.getId() == R.id.btn_play_again){
            Intent in = new Intent(OnGameOverActivity.this, GameField.class);
            startActivity(in);
        }

    }
}
