package nkichev.wooanna.octopusgameteamwork.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Woo on 15.10.2014 г..
 */
public class QuestionActivity extends Activity {
    String q;
    String[] a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        if(getIntent().hasExtra(GameField.Q)){
           q = bundle.getString(GameField.Q);
        }
        if(getIntent().hasExtra(GameField.A)){
           a = bundle.getStringArray(GameField.A);
        }

        alertDialog(a, q);

    }

    private void alertDialog(String[] a, String q) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(q)
                .setItems(a, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        QuestionActivity.this.finish();
                    }
                });

        builder.create();
       builder.show();

    }
}
