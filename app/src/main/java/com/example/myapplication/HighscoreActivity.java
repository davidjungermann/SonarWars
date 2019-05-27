package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class HighscoreActivity extends AppCompatActivity {
    MainActivity activity;
    public static int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        TextView scoreView = (TextView)findViewById(R.id.textView8);

        SharedPreferences scorePrefs = getSharedPreferences(activity.GAME_PREFS, 0);

        String[] savedScores = scorePrefs.getString("highScores", "").split("\\|");

        StringBuilder scoreBuild = new StringBuilder("");
        for(String score : savedScores){
            scoreBuild.append(score+"\n");

        }

        scoreView.setText(scoreBuild.toString());
    }


}