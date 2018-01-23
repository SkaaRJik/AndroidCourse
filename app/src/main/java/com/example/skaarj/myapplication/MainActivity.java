package com.example.skaarj.myapplication;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText textExpression;
    String[] films;
    boolean[] showedFilms;
    int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.textExpression = (EditText) this.findViewById(R.id.textExpression);

        this.films = getResources().getStringArray(R.array.films);
        this.showedFilms = new boolean[films.length];
        this.counter = this.films.length;

    }

    public void addTextOnButtonToExpression(View button){
        if(button instanceof Button){
            this.textExpression.append( ((Button) button).getText() );
        }
    }

    public void clear(View view){
        textExpression.setText("");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void calculate(View view){
        try {
            textExpression.append(" = ");
            double result = new Calculator(textExpression.getText().toString()).getResult();
            textExpression.append(String.valueOf(result));
        } catch (CalculationException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void showFilm(View view){
        int i = (int) (Math.random() * films.length);
        if(counter !=0 ) {
            while (this.showedFilms[i]) {
                i = (int) (Math.random() * films.length);
            }
            this.showedFilms[i] = true;
            this.textExpression.setText(films[i]);
            this.counter--;
        }
        else {
            Toast.makeText(this, "Фильмы кончились", Toast.LENGTH_SHORT).show();
        }
    }

    public void refresh(View view){
        for (int i = 0; i < this.showedFilms.length; i++) {
            this.showedFilms[i] = false;
        }
        this.textExpression.setText("");
        this.counter = this.films.length;
    }

    public void goPlay(View view){
        this.startActivity(new Intent(MainActivity.this, GameActivity.class));
    }
}
