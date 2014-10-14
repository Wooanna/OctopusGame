package nkichev.wooanna.octopusgameteamwork;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created by Woo on 13.10.2014 г..
 */
public class GameObject {
    private int xPosiotion;
    private int yPosition;
    private int size;
    private String type;
    private Bitmap image;
    private boolean isOutOfSpace;
    private int screenHeight;

    public GameObject(Context context, String type, int xPosition, Bitmap image){
        this.type = String.valueOf(type);
        this.xPosiotion = xPosition;
        this.yPosition = 0;
        this.image = image;
        this.size = image.getWidth();
        this.isOutOfSpace = false;
        this.screenHeight = context.getResources().getDisplayMetrics().heightPixels;
    }
    private void update(){
        this.yPosition +=5;
        if(this.yPosition >= this.screenHeight){
            isOutOfSpace = true;
        }
    }
    public void draw(Canvas c){
        update();
       c.drawBitmap(image, this.xPosiotion, this.yPosition, null);
    }
     public int getY(){
         return this.yPosition;
     }

    public boolean isOutOfSpace(){
        return this.isOutOfSpace;
    }

}
