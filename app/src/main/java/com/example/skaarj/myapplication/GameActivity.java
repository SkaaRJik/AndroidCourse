package com.example.skaarj.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.StringTokenizer;

public class GameActivity extends AppCompatActivity {

    private EditText editTextMin;
    private EditText editTextMax;
    private EditText editTextURI;
    Button buttonStart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        editTextMin = (EditText) this.findViewById(R.id.textEditMin);
        editTextMax = (EditText) this.findViewById(R.id.textEditMax);
        buttonStart = (Button) this.findViewById(R.id.buttonPlay);
        editTextURI = (EditText) this.findViewById(R.id.textViewURI);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(GameActivity.this, QuestionActivity.class);
                intent.putExtra("min", Integer.parseInt(editTextMin.getText().toString()));
                intent.putExtra("max", Integer.parseInt(editTextMax.getText().toString()));
                view.getContext().startActivity(intent);
            }
        });

    }

    protected void startIntent(View view){
        StringTokenizer str = new StringTokenizer(editTextURI.getText().toString());
        if(str.countTokens() >= 2) {
            String mode = "";
            Uri uri = null;
            if (str.hasMoreTokens()) mode = str.nextToken();
            if (str.hasMoreTokens()) {
                    uri = Uri.parse(str.nextToken());
                    switch (mode) {
                        case "tel":
                            Intent intent = new Intent(Intent.ACTION_CALL, uri);
                            break;

                    }

            }
        }
    }

    protected void showSnowman(View view){
       this.startActivity( new Intent(this, SnowmanActivity.class) );
    }
}
