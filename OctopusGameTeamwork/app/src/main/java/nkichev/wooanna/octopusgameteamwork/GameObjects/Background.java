package nkichev.wooanna.octopusgameteamwork.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import nkichev.wooanna.octopusgameteamwork.Activities.GameField;

public class Background {

    Bitmap image;
    GameField.OurGameView view;
    int xPositionBgr;
    int yPosiotionBgr;
    int screenWidth;
    int screenHeight;
    int speed;
    int bottomOfImage;

    public Background(GameField.OurGameView view, Bitmap image, int xmax, int ymax) {
        this.image = image;
        this.xPositionBgr = 0;
        this.screenHeight = ymax;
        this.screenWidth = xmax;
        this.yPosiotionBgr = this.screenHeight - image.getHeight();
        this.bottomOfImage = this.screenHeight;
        this.speed = 10;
    }

    private void update(){
        if(this.yPosiotionBgr == 0){
            this.yPosiotionBgr = this.screenHeight - image.getHeight();
            this.bottomOfImage += this.speed;
        }
        this.yPosiotionBgr +=this.speed;
    };
    public void draw(Canvas c){
        update();
        Rect src = new Rect(0, 0, image.getWidth(), this.screenHeight);
        Rect dst = new Rect(0, this.yPosiotionBgr, this.screenWidth, this.yPosiotionBgr + image.getHeight());
        c.drawBitmap(image,src, dst, null);
      }
}
