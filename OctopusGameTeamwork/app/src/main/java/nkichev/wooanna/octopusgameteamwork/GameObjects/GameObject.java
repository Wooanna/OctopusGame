package nkichev.wooanna.octopusgameteamwork.GameObjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Woo on 13.10.2014 г..
 */
public class GameObject {
    private int xPosition;
    private int yPosition;
    private int size;
    private String type;
    private Bitmap image;
    private boolean isOutOfSpace;
    private int screenHeight;

    public GameObject(Context context, String type, int xPosition, Bitmap image){
        this.type = String.valueOf(type);
        this.xPosition = xPosition;
        this.yPosition = 0 - image.getHeight();
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
       c.drawBitmap(image, this.xPosition, this.yPosition, null);
    }

    public int getY(){
         return this.yPosition;
     }

    public int getX(){
        return this.xPosition;
    }

    public int getSize(){
        return this.size;
    }

    public String getType() { return this.type; }


    public boolean isOutOfSpace(){
        return this.isOutOfSpace;
    }

    public void setIsOutOfSpace(boolean state){
        this.isOutOfSpace = state;
    }

}
