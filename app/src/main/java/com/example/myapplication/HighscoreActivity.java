package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class HighscoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        TextView scoreView = findViewById(R.id.textView8);
        SharedPreferences scorePrefs = getSharedPreferences(MainActivity.GAME_PREFS, 0);
        String[] savedScores = scorePrefs.getString("highScores", "").split("\\|");
        StringBuilder scoreBuild = new StringBuilder();

        for (String score : savedScores) {
            scoreBuild.append(score + " points" + "\n");
        }
        scoreView.setText(scoreBuild.toString());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(HighscoreActivity.this, MenuActivity.class));
    }
}