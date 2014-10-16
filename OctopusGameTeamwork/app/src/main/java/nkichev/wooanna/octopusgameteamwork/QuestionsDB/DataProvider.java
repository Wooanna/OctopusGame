package nkichev.wooanna.octopusgameteamwork.QuestionsDB;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.telerik.everlive.sdk.core.EverliveApp;
import com.telerik.everlive.sdk.core.result.RequestResult;

import java.util.ArrayList;
import java.util.List;

import nkichev.wooanna.octopusgameteamwork.CollisionManager;

/**
 * Created by Woo on 15.10.2014 г..
 */
public  class DataProvider {
    EverliveApp app;
    RequestResult<ArrayList<Question>> requestResult;
    ArrayList<Question> allQuestions;
    private CollisionManager.CallBack callback;


    public void provideData() {
     app = new EverliveApp("EXFG9vYl22Jp9Arp");

         new Thread(new Runnable() {

            @Override
            public void run() {
                requestResult = app.workWith().data(Question.class).getAll().executeSync();

                if (requestResult.getSuccess()) {
                    Log.d("SUCCESS", "IN SUCCESS");
                    allQuestions = requestResult.getValue();
                    callback.onSuccess(allQuestions);

                    for (Question question : allQuestions) {

                        Log.d("QUESTION", "retrieved question: " + question.getQuestion());
                        String[] answers = question.getAnswers();
                        for (String answer : answers) {
                            Log.d("ANS", answer);
                        }
                    }

                } else {
                    System.out.println(requestResult.getError().toString());
                }
            }

        }).start();

    }

    public ArrayList<Question> getQuestion(){
        return this.allQuestions;
    }

    public void onDataLoaded(CollisionManager.CallBack callBack) {
        this.callback = callBack;
    }
}

