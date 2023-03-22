package com.example.geoquiz;

import androidx.annotation.StringRes;

final class Question {@StringRes private int textResId; private boolean answer;

    public Question(int textResId, boolean answer){
        this.textResId = textResId;
        this.answer = answer;
    }

    boolean getAnswer(){
        return this.answer;
    }

    int getTextResId() {
        return this.textResId;
    }
}