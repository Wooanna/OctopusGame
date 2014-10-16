package nkichev.wooanna.octopusgameteamwork.Activities;

import android.app.Activity;
import android.content.Context;
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

import nkichev.wooanna.octopusgameteamwork.GameObjects.Background;
import nkichev.wooanna.octopusgameteamwork.GameObjects.CollisionManager;
import nkichev.wooanna.octopusgameteamwork.GameAudio.Assets;
import nkichev.wooanna.octopusgameteamwork.GameAudio.GameAudio;
import nkichev.wooanna.octopusgameteamwork.GameObjects.GameObject;
import nkichev.wooanna.octopusgameteamwork.GameObjects.GameObjectManger;
import nkichev.wooanna.octopusgameteamwork.GameObjects.Octopus;
import nkichev.wooanna.octopusgameteamwork.QuestionsDB.Question;
import nkichev.wooanna.octopusgameteamwork.R;
import nkichev.wooanna.octopusgameteamwork.GameObjects.Score;

public class GameField extends Activity implements SensorEventListener, GestureDetector.OnGestureListener {

    private OurGameView v;
    private Bitmap octopus;
    private Bitmap moving_back;
    public float xmax,ymax;
    public float xPosition;
    public float yPosition;

    private SensorManager sensorManager = null;
    public Sensor accelerometer;
    private Octopus creature;
    private Background background;
    private GameObjectManger gameObjectManager;
    public List<GameObject> gameObjects;
    private Random random;
    private  GestureDetector detector;
    private Intent intent;
    private CollisionManager collisionManager;
    private GameAudio gameAudio;
    private Score scoreDrawer;
    private int collisionOffset;
    private int bottomOffset;

    public static final  String Q = "Q";
    public static final String A = "A";
    public static final String SCORE = "SCORE";
    public static long score;

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
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        GetScreenDimentions();
        random = new Random();

        gameObjectManager = new GameObjectManger(this);
        collisionManager = new CollisionManager(GameField.this);
        gameObjects = new ArrayList<GameObject>();
        this.detector = new GestureDetector(this, this);
        this.gameAudio = new GameAudio(this);
        Assets.blop = gameAudio.newSound("blop.wav");
        Assets.bell = gameAudio.newSound("bell.wav");
        Assets.gameover = gameAudio.newSound("gameover.wav");

        this.score = 0;
        this.collisionOffset = 50;
        this.bottomOffset = 80;

        setContentView(v);
    }

    @Override
    protected void onStart() {
        super.onStart();

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
     //   Assets.submarine.play();
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

            if (yPosition > ymax - creature.getHeight() - bottomOffset ) {
                yPosition = ymax - creature.getHeight() - bottomOffset;
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
           intent = new Intent(GameField.this, OnPause.class);
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
            creature = new Octopus(OurGameView.this, octopus, xmax/2, ymax);
            background = new Background(OurGameView.this, moving_back, (int)xmax, (int)ymax);
            scoreDrawer = new Score();
            while(isItOk) {

                //perform canvas drawing
                if(!holder.getSurface().isValid()){
                    continue;
                }
                if( random.nextInt(50) == 5){
                    GameObject obj = gameObjectManager.initObject();
                     gameObjects.add(obj);
                }

                //check for collision
                for (GameObject obj : gameObjects){
                    if (ifCollide(creature, obj)){

                        if(obj.getType() == "Question" && !obj.isOutOfSpace()){
                             Assets.blop.play(1);
                            startQuestionActivity();
                            score *=2;
                        }else  if(obj.getType() == "Star" && !obj.isOutOfSpace()){
                          score +=45;
                          Assets.bell.play(1);
                        }else if(obj.getType() == "Enemy" && !obj.isOutOfSpace()){
                            Assets.gameover.play(1);
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

            if (octopusBottom <= objectTop + collisionOffset){
                return false;
            }
            if (octopusTop >= objectBottom - collisionOffset){
                return false;
            }
            if (octopusRight <= objectLeft + collisionOffset){
                return false;
            }
            if (octopusLeft >= objectRight - collisionOffset){
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
             scoreDrawer.draw(canvas, score);
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

        Intent i  = new Intent(GameField.this, QuestionActivity.class);
        i.putExtra(Q, q.getQuestion());
        i.putExtra(A, q.getAnswers());
        startActivity(i);
    }

   }
