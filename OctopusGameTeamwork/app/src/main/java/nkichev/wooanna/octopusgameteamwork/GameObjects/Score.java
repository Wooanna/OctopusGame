package nkichev.wooanna.octopusgameteamwork.GameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import nkichev.wooanna.octopusgameteamwork.Activities.GameField;

/**
 * Created by Woo on 16.10.2014 г..
 */
public class Score {

    int xPositionBgr;
    int yPosiotionBgr;
    GameField.OurGameView view;
    public Score(){

    }

    public void draw(Canvas c, long score){

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
          String text = "SCORE: " + String.valueOf(score);
       // c.drawPaint(paint);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
         paint.setTextSize(32);

        Rect rectText = new Rect();
        paint.getTextBounds(text, 0, text.length(), rectText);

        c.drawText(text,
                0, rectText.height(), paint);


      //  c.drawText(String.valueOf(score),0, 0, paint);
    }
}
