package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HighscoreActivity extends AppCompatActivity {
    MainActivity activity;
    public static int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        final Button back = findViewById(R.id.button4);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                startActivity(new Intent(HighscoreActivity.this, MenuActivity.class));
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        back.getBackground().setColorFilter(null);
                    }
                }, 100);
            }
        });

        TextView scoreView = findViewById(R.id.textView8);

        SharedPreferences scorePrefs = getSharedPreferences(MainActivity.GAME_PREFS, 0);

        String[] savedScores = scorePrefs.getString("highScores", "").split("\\|");

        StringBuilder scoreBuild = new StringBuilder();
        for (String score : savedScores) {
            scoreBuild.append(score + "\n");

        }

        scoreView.setText(scoreBuild.toString());
    }


}