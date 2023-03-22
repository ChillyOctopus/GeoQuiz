package com.example.geoquiz;

import android.util.Log;

import androidx.lifecycle.ViewModel;

public class QuizViewModel extends ViewModel {

    private final String TAG = "QuizViewModel";

    private Question[] questionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)};

    private int currentIndex = 0;
    private boolean isCheater = false;

    public boolean getCurrentQuestionAnswer(){
        return questionBank[currentIndex].getAnswer();
    }

    public int getCurrentQuestionText(){
        return questionBank[currentIndex].getTextResId();
    }

    public void setCheater(boolean value){
        isCheater = value;
    }

    public boolean isCheater(){
        return isCheater;
    }

    public void moveToNext(){
        currentIndex = (currentIndex+1) % questionBank.length;
    }

    public QuizViewModel() {
        super();
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }


}
