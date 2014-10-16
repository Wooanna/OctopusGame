package nkichev.wooanna.octopusgameteamwork.QuestionsDB;

import android.os.Parcel;
import android.os.Parcelable;

import com.telerik.everlive.sdk.core.model.base.DataItem;
import com.telerik.everlive.sdk.core.serialization.ServerProperty;
import com.telerik.everlive.sdk.core.serialization.ServerType;

/**
 * Created by Woo on 15.10.2014 г..
 */
@ServerType("Question")
public class Question extends DataItem  {
    @ServerProperty("Question")
    public String question;

    @ServerProperty("Answers")
    public String[] answers;

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getAnswers() {
        return this.answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

   }

