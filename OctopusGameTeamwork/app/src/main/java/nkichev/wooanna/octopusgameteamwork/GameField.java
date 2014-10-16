package nkichev.wooanna.octopusgameteamwork;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nkichev.wooanna.octopusgameteamwork.QuestionsDB.Question;

public class GameField extends Activity implements SensorEventListener, GestureDetector.OnGestureListener {

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
    private  GestureDetector detector;
    Intent intent;
    private CollisionManager collisionManager;
    public static final  String Q = "Q";
    public static final String A = "A";
    public static final String SCORE = "SCORE";

    public static long score ;



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
        collisionManager = new CollisionManager(GameField.this);
        gameObjects = new ArrayList<GameObject>();
        this.detector = new GestureDetector(this, this);
        this.score = 0;
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
    public boolean onTouchEvent(MotionEvent event) {
        return this.detector.onTouchEvent(event);
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
            if (yPosition > ymax - creature.getHeight() ) {
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
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
           intent = new Intent(GameField.this, ActivityPoused.class);
        startActivity(intent);

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        return false;
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

                //check for collision
                for (GameObject obj : gameObjects){
                    if (ifCollide(creature, obj)){

                        if(obj.getType() == "Question" && !obj.isOutOfSpace()){
                            startQuestionActivity();
                        }else  if(obj.getType() == "Star" && !obj.isOutOfSpace()){
                          score +=45;
                        }else if(obj.getType() == "Enemy" && !obj.isOutOfSpace()){
                            gameOver();
                        }else if(obj.getType() == "Present" && !obj.isOutOfSpace()){

                        }
                        obj.setIsOutOfSpace(true);
                    }
                }

                Canvas canvas = holder.lockCanvas();
                drawScene(canvas);
                holder.unlockCanvasAndPost(canvas);
            }
        }

        protected boolean ifCollide(Octopus octopus, GameObject object){
            float octopusLeft = octopus.getX();
            float octopusRight = octopus.getX() + octopus.getWidth();
            float octopusTop = octopus.getY();
            float octopusBottom = octopus.getY() + octopus.getHeight();

            float objectLeft = object.getX();
            float objectRight = object.getX() + object.getSize();
            float objectTop = object.getY();
            float objectBottom = object.getY() + object.getSize();

            if (octopusBottom <= objectTop){
                return false;
            }
            if (octopusTop >= objectBottom){
                return false;
            }
            if (octopusRight <= objectLeft){
                return false;
            }
            if (octopusLeft >= objectRight){
                return false;
            }

            return true;
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

    private void gameOver() {
      Intent gameOver = new Intent(GameField.this, OnGameOverActivity.class);
        gameOver.putExtra(SCORE, score);
        startActivity(gameOver);
    }

    private void startQuestionActivity() {
        Question q = collisionManager.onQuestionCollision();

        Intent i  = new Intent(this, QuestionActivity.class);
        i.putExtra(Q, q.getQuestion());
        i.putExtra(A, q.getAnswers());
        startActivity(i);
        // alertDialog(collisionManager.onQuestionCollision());
    }

    private void alertDialog(Question currentQuestion) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] currentQuestionAnswers = currentQuestion.getAnswers();

        builder.setTitle(currentQuestion.getQuestion())
                .setItems(currentQuestionAnswers, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        builder.create();
        builder.show();

    }
}
