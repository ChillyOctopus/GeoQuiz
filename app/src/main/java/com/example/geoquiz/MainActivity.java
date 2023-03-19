package com.example.geoquiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geoquiz.*;
public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private final String KEY_INDEX = "index";
    private final int REQUEST_CODE_CHEAT = 0;

    private Button trueButton;
    private  Button falseButton;
    private Button nextButton;
    private Button cheatButton;
    private TextView questionTextView;

    private QuizViewModel getQuizViewModel(){
        return ViewModelProviders.of(this).get(QuizViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        cheatButton = findViewById(R.id.cheat_button);
        questionTextView = findViewById(R.id.question_text_view);

        int currentIndex = 0;
        if(savedInstanceState != null &&
        savedInstanceState.containsKey(KEY_INDEX)){
            currentIndex = savedInstanceState.getInt(KEY_INDEX);
        }

        getQuizViewModel().setCurrentIndex(currentIndex);

        trueButton.setOnClickListener(new View.OnClickListener() {
           @Override
               public void onClick(View view){
               checkAnswer(true);
           }
        });

        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CheatActivity.class);
                startActivityForResult(intent,REQUEST_CODE_CHEAT);
            }

        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                checkAnswer(false);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuizViewModel().moveToNext();
                updateQ();
            }
        });
        updateQ();

        //End of "OnCreate" function
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode != Activity.RESULT_OK){
            return;
        }

        if(requestCode == REQUEST_CODE_CHEAT){
            boolean isCheater = false;
            if(data != null &&
                data.hasExtra(CheatActivity.EXTRA_ANSWER_SHOWN)){
                isCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
            }
            getQuizViewModel().setCheater(isCheater);
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");

    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(
                KEY_INDEX, getQuizViewModel().getCurrentIndex());
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");

    }
    private void updateQ() {
        int questionTextResId = getQuizViewModel().getCurrentQuestionText();
        questionTextView.setText(questionTextResId);
    }

    private void checkAnswer(boolean userAns){
        boolean correctAnswer = getQuizViewModel().getCurrentQuestionAnswer();
        int messageResId;
        if (getQuizViewModel().isCheater()){
            messageResId = R.string.judgment_toast;
        }else{
            messageResId = ((userAns == correctAnswer) ? R.string.correct_toast : R.string.incorrect_toast);
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    //End of "MainActivity" function
}