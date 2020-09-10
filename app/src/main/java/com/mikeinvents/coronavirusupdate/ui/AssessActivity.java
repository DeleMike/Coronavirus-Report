package com.mikeinvents.coronavirusupdate.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mikeinvents.coronavirusupdate.R;

import java.util.ArrayList;

public class AssessActivity extends AppCompatActivity {
    //private static final String TAG = "AssessActivity";
    public static final String NAME = "ANSWERS";

    private static final String[] QUESTIONS = {
            "Have you recently traveled to an area with known local spread of COVID-19 ?",

            "Have you come in close contact (within 6 feet) with someone who has a lab confirmed COVID-19 ?",

            "Do you have a fever (greater than 100.4 degrees Fahrenheit or 38.0 degrees Celsius) \nor" +
                    " symptoms of lower respiratory illness such as cough, shortness of breath, " +
                    "difficulty in breathing or some sore throat, \nare you feeling tired? Any headache?",

            "Are you a first responder, Health Care worker, or employee or attendee of a child or adult care facility"
    };

    private TextView questionHeader;
    private RadioButton yesButton, noButton;
    private Button finishButton;
    int questionIndex = 0;
    private RadioGroup radioGroup;
    private ArrayList<String> answers = new ArrayList<>();
    private boolean answered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assess);

        questionHeader = findViewById(R.id.assess_question);
        yesButton = findViewById(R.id.question_option_yes);
        noButton = findViewById(R.id.question_option_no);
        finishButton = findViewById(R.id.question_button);
        radioGroup = findViewById(R.id.question_radio_group);
        loadQuestions();

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!answered){
                    if(yesButton.isChecked() || noButton.isChecked()){
                        checkAnswer();
                    }else {
                        Toast.makeText(AssessActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    loadQuestions();
                }
            }
        });
    }

    private void loadQuestions() {
        if(yesButton.isChecked() || noButton.isChecked()){
            radioGroup.clearCheck();
        }

        int totalQuestion = QUESTIONS.length;
        if (questionIndex < totalQuestion) {
            questionHeader.setText(QUESTIONS[questionIndex]);
            answered = false;
            questionIndex++;
            if (questionIndex == totalQuestion) {
                finishButton.setText(getString(R.string.finish_str));
            } else {
                finishButton.setText(getString(R.string.next_str));
            }
        }else{
            finishQuiz();
        }
        
    }

    private void finishQuiz() {
        System.out.println(answers);
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(NAME, answers);
        startActivity(intent);
        finish();
    }

    private void checkAnswer() {
        answered = true;
        String selectedAns;
        RadioButton selectedButton = findViewById(radioGroup.getCheckedRadioButtonId());

        if(selectedButton == null){
            selectedAns = "";
        }else {
            selectedAns = selectedButton.getText().toString();
           // Log.i(TAG,"Selected answer = "+selectedAns);
        }
        answers.add(selectedAns);
        loadQuestions();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("questionIndex",questionIndex);
        outState.putBoolean("answered",answered);
        outState.putStringArrayList("answers", answers);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null){
            questionIndex = savedInstanceState.getInt("questionIndex");
            answered = savedInstanceState.getBoolean("answered");
            questionHeader.setText(QUESTIONS[questionIndex - 1]);
            answers = savedInstanceState.getStringArrayList("answers");
        }
    }
}
