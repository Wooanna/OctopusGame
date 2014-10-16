package nkichev.wooanna.octopusgameteamwork.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import nkichev.wooanna.octopusgameteamwork.R;

/**
 * Created by Woo on 14.10.2014 г..
 */
public class InstructionsActivity extends Activity implements GestureDetector.OnGestureListener{

    private ImageView star;
    private ImageView question;
    private ImageView present;
    private ImageView rightArrow;
    private TextView star_text;
    private TextView question_text;
    private TextView present_text;
    private String STAR_TEXT = "Collect stars and gain score";
    private String QUESTION_TEXT = "Be smart and double your score";
    private String PRESENT_TEXT = "Get the chance for many presents";
    private Intent intent;
    private  GestureDetector detector;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instruct_layout);

        star = (ImageView)findViewById(R.id.star);
        question = (ImageView)findViewById(R.id.question);
        present = (ImageView)findViewById(R.id.present);
        rightArrow = (ImageView)findViewById(R.id.rightArrow);
        star.setImageResource(R.drawable.star);
        question.setImageResource(R.drawable.question_mark);
        present.setImageResource(R.drawable.present);
        rightArrow.setImageResource(R.drawable.right_arrow);
        Typeface font = Typeface.createFromAsset(getAssets(), "BEERNOTE.ttf");
        star_text = (TextView)findViewById(R.id.star_text);
        question_text = (TextView)findViewById(R.id.question_text);
        present_text = (TextView)findViewById(R.id.present_text);
        star_text.setText(STAR_TEXT);
        question_text.setText(QUESTION_TEXT);
        present_text.setText(PRESENT_TEXT);
        star_text.setTypeface(font);
        question_text.setTypeface(font);
        present_text.setTypeface(font);
        this.detector = new GestureDetector(this, this);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.detector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        intent  = new Intent(InstructionsActivity.this, SecondPageInstructionsActivity.class);
        this.startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        return false;
    }
}
