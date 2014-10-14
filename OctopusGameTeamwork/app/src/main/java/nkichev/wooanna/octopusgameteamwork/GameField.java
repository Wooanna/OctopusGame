package nkichev.wooanna.octopusgameteamwork;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameField extends Activity implements SensorEventListener {

    OurGameView v;
    Bitmap octopus;
    Bitmap moving_back;
    //public float  xAcceleration,xVelocity = 0.0f;
    //public float  yAcceleration,yVelocity = 0.0f;
    public float xmax,ymax;
    public float xPosition;
    public float yPosition;
    private SensorManager sensorManager = null;
    public Sensor accelerometer;
   //public float frameTime = 0.666f;
    Octopus creature;
    Background background;
    GameObject object;
    GameObjectManger gameObjectManager;
   public List<GameObject> gameObjects;
    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        v = new OurGameView(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        octopus = BitmapFactory.decodeResource(getResources(), R.drawable.octopus_sprite);
        moving_back = BitmapFactory.decodeResource(getResources(), R.drawable.moving_bgr);

        // Get a reference to a SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        GetScreenDimentions();
        random = new Random();
        gameObjectManager = new GameObjectManger(this);
        gameObjects = new ArrayList<GameObject>();
        setContentView(v);
    }

    private void GetScreenDimentions() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        xmax = size.x;
        ymax = size.y;
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        v.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Register this class as a listener for the accelerometer sensor
        sensorManager.registerListener(this, accelerometer,
                SensorManager.SENSOR_DELAY_GAME);
        v.resume();
    }

    @Override
    protected void onStop()
    {
        // Unregister the listener
        sensorManager.unregisterListener(this);
        super.onStop();
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {


        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (xPosition > xmax - creature.getWidth()) {
                xPosition = xmax- creature.getWidth();
            } else if (xPosition < 0) {
                xPosition = 0;
            }
            //something is wrong here and we need to fix the bug!!(image is going a bit down than it has to!
            //It is probably because of not calculating the height of the upper bar on the screen!!!!
            if (yPosition > ymax - creature.getHeight()) {
                yPosition = ymax - creature.getHeight();
            } else if (yPosition < 0) {
                yPosition = 0;
            }
            xPosition -= (int) sensorEvent.values[0];
            yPosition += (int) sensorEvent.values[1];
            creature.setX((int)xPosition);
            creature.setY((int)yPosition);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //unimplemented for now, we ain't gonna need it
    }

    public class OurGameView extends SurfaceView implements Runnable {

        Thread t = null;
        SurfaceHolder holder;
        boolean isItOk = false;

        public OurGameView(Context context){
            super(context);
            holder = getHolder();
        }

        public void run() {
            creature = new Octopus(OurGameView.this, octopus);
            background = new Background(OurGameView.this, moving_back, (int)xmax, (int)ymax);
            while(isItOk) {

                //perform canvas drawing
                if(!holder.getSurface().isValid()){
                    continue;
                }
                if( random.nextInt(80) == 5){
                    GameObject obj = gameObjectManager.initObject();
                     gameObjects.add(obj);
                }

                Canvas canvas = holder.lockCanvas();
                //do drawing here
                drawScene(canvas);
                holder.unlockCanvasAndPost(canvas);
            }
        }

        protected void drawScene(Canvas canvas){
            background.draw(canvas);
             for(GameObject obj: gameObjects){
                 if(!obj.isOutOfSpace()){

                     obj.draw(canvas);
                 }

             }
             creature.draw(canvas);
        }

        public void pause(){
            isItOk = false;
            while(true){
                try{
                    t.join();
                }catch(InterruptedException e){
                    //handle exception
                    e.printStackTrace();
                }
                break;
            }
            t = null;
        }

        public void resume(){
            isItOk = true;
            t = new Thread(this);
            t.start();
        }
    }
}
