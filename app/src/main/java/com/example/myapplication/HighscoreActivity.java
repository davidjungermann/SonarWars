package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class HighscoreActivity extends AppCompatActivity {
    MainActivity activity;
    public static int score;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = MainActivity.getSharedInstance();
        GameScene scene = (GameScene) activity.mCurrentScene;
        score = scene.points;

        if (score != 0) {
            ArrayList<Integer> list = new ArrayList<>();
            list.add(score);
            System.out.println("Scores: " + list);
        }
    }

}
