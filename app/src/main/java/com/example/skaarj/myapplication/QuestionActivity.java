package com.example.skaarj.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class QuestionActivity extends AppCompatActivity {

    TextView textViewInterval;
    TextView textViewHint;
    EditText editTextGuess;
    Button buttonSendGuess;

    int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        this.textViewInterval = ( TextView) findViewById(R.id.textViewInterval);
        this.textViewHint = ( TextView) findViewById(R.id.textViewHint);
        this.editTextGuess = ( EditText ) findViewById(R.id.editTextGuess);
        this.buttonSendGuess = (Button) findViewById(R.id.buttonSendGuess);
        int min=0, max=0;
        if (this.getIntent() != null) {
            Intent intent = this.getIntent();
            min = intent.getIntExtra("min", 0);
            max = intent.getIntExtra("max", 0);
            textViewInterval.append("от " + min + " до " + max);
            this.number = min + (int)(Math.random() * ((max - min) + 1));
        }
        else {
            textViewInterval.setText("Не смогли сгененрировать число :(");
        }
    }

    public void makeGuess(View view) {

        textViewHint.setVisibility(View.VISIBLE);
        if(editTextGuess.getText().length() == 0){
            textViewHint.setText("Вы забыли ввести число!");
        }
        else{
            int guess = Integer.parseInt(editTextGuess.getText().toString());
            if(guess < this.number) textViewHint.setText(R.string.greater);
            else if(guess > this.number) textViewHint.setText(R.string.less);
            else{
                textViewHint.setText(R.string.equal);
                buttonSendGuess.setVisibility(View.INVISIBLE);
            }

        }
    }
}
