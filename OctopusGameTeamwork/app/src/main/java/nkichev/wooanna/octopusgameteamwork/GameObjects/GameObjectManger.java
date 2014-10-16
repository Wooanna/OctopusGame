package nkichev.wooanna.octopusgameteamwork.GameObjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

import nkichev.wooanna.octopusgameteamwork.R;

/**
 * Created by Woo on 13.10.2014 г..
 */
public class GameObjectManger {
    Random random;
    int screenWidth;
    String[] gameObjectTypes = new String[]{"Star", "Question", "Enemy", "Present"};
    int xPosition;
    String type;
    Context context;
    Bitmap image;

    public GameObjectManger(Context context){
        this.context = context;
        this.random = new Random();
      this.screenWidth = context.getResources().getDisplayMetrics().widthPixels;
    }
    public GameObject initObject(){
        int randomNumber = this.random.nextInt(10);

        if(randomNumber == 0){
            this.type = this.gameObjectTypes[2];
            this.image = BitmapFactory.decodeResource(context.getResources(), R.drawable.monster);
        }else if(randomNumber >=1 && randomNumber <=7){
            this.type = this.gameObjectTypes[0];
            this.image = BitmapFactory.decodeResource(context.getResources(), R.drawable.star);
        }else if(randomNumber >= 8 && randomNumber <=9){
            this.type = this.gameObjectTypes[1];
            this.image = BitmapFactory.decodeResource(context.getResources(), R.drawable.question_mark);
        }else {
            this.type = this.gameObjectTypes[3];
            this.image = BitmapFactory.decodeResource(context.getResources(), R.drawable.present);
        }

        this.xPosition = this.random.nextInt(this.screenWidth - this.image.getWidth());
        return new GameObject(this.context, this.type, this.xPosition, this.image);

    }

}
