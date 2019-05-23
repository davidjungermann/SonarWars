package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);
        Button playGame = findViewById(R.id.button2);
        Button tutorial = findViewById(R.id.button3);
        Button highScore = findViewById(R.id.button);

        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, TutorialActivity.class));
            }
        });

        playGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, MainActivity.class));

            }
        });

        highScore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }
}
