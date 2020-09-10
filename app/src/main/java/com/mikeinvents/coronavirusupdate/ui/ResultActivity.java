package com.mikeinvents.coronavirusupdate.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mikeinvents.coronavirusupdate.R;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    private static final String CALL_NUMBER = "080097000010";

    private ArrayList<String> answerList;
    private TextView answerMsg;
    private Button callButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        answerMsg = findViewById(R.id.result_answer);
        Button finishButton = findViewById(R.id.quiz_result_finish);
        callButton = findViewById(R.id.quiz_result_call);

        callButton.setEnabled(false);
        callButton.setVisibility(View.GONE);

        Intent intent = getIntent();
        answerList = intent.getStringArrayListExtra(AssessActivity.NAME);
        System.out.println(answerList);

        analyzeResult();

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + CALL_NUMBER));
                startActivity(intent);
            }
        });
    }

    private void analyzeResult() {
        for(int i=0; i<answerList.size(); i++){
            if(answerList.get(2).equalsIgnoreCase("yes")){
                answerMsg.setText(getString(R.string.result_priority_one_answer));
                callButton.setEnabled(true);
                callButton.setVisibility(View.VISIBLE);
            }else if(answerList.get(0).equalsIgnoreCase("yes") ||
                        answerList.get(1).equalsIgnoreCase("yes")){
                answerMsg.setText(getString(R.string.result_priority_two_answer));
            }else{
                answerMsg.setText(getString(R.string.result_priority_three_answer));
            }
        }
    }
}
