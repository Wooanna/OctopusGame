package nkichev.wooanna.octopusgameteamwork.GameObjects;

import android.content.Context;

import java.util.ArrayList;
import java.util.Random;

import nkichev.wooanna.octopusgameteamwork.QuestionsDB.DataProvider;
import nkichev.wooanna.octopusgameteamwork.QuestionsDB.Question;


/**
 * Created by Woo on 15.10.2014 г..
 */
public class CollisionManager {
    private GameObject obj;
    private Context context;
    private ArrayList<Question> allQuestions;
    private DataProvider dataProvider;

    private Random random;
    private String[] currentQuestionAnswers;
    private Question currentQuestion;

    public interface CallBack {
        void onSuccess(ArrayList<Question> data);
    }

    public CollisionManager(Context context){
        this.context = context;
       dataProvider = new DataProvider();
      dataProvider.provideData();
        dataProvider.onDataLoaded(new CallBack() {
            @Override
            public void onSuccess(ArrayList<Question> data) {
                allQuestions = data;
            }
        });
        random = new Random();
    }

    public Question onQuestionCollision(){
             return allQuestions.get(random.nextInt(allQuestions.size()));
    }

    public void onStarCollision(){

    }

    public void onEnemyCollision(){

    }

    public void onPresentCollision(){

    }


}
