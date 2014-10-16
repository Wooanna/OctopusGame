package nkichev.wooanna.octopusgameteamwork;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Woo on 13.10.2014 г..
 */
public class Octopus {

    private Bitmap bitmap; // the actual bitmap
    private float x;   // the X coordinate
    private float y;   // the Y coordinate
    private float width;
    private float height;
    private boolean touched; // if monkey is touched
    private int currentFrame = 0;
    GameField.OurGameView ov;

    public Octopus(GameField.OurGameView view, Bitmap bitmap) {
        this.bitmap = bitmap;
        this.height = bitmap.getHeight();
        this.width = bitmap.getWidth()/6;
        this.x = 0;
        this.y = 0;
        ov = view;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public float getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public float getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }


    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }


    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }


    public void draw(Canvas canvas) {
        currentFrame = ++currentFrame %6;
        int srcX = this.currentFrame* (int)this.width;

        Rect src = new Rect(srcX, 0, (currentFrame+1)*(int)width, (int)height);
        Rect dst= new Rect((int)x, (int)y,  (int)x +(int)width,  (int)height + (int)y);
        canvas.drawBitmap(bitmap, src, dst, null);
    }


}
