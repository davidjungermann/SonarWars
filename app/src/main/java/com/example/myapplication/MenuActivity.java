package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);
        final Button playGame = findViewById(R.id.button2);
        final Button tutorial = findViewById(R.id.button3);
        final Button highScore = findViewById(R.id.button);

        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tutorial.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                startActivity(new Intent(MenuActivity.this, TutorialActivity.class));

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tutorial.getBackground().setColorFilter(null);
                    }
                }, 100);

            }
        });

        playGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playGame.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                startActivity(new Intent(MenuActivity.this, MainActivity.class));
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playGame.getBackground().setColorFilter(null);
                    }
                }, 100);
            }
        });

        highScore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                highScore.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                startActivity(new Intent(MenuActivity.this, HighscoreActivity.class));
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        highScore.getBackground().setColorFilter(null);
                    }
                }, 100);
            }
        });
    }
}
