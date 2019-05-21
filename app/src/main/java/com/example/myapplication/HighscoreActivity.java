package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.andengine.entity.scene.background.Background;
import org.andengine.util.adt.color.Color;

import java.util.ArrayList;
import java.util.Collections;

public class HighscoreActivity extends AppCompatActivity {
    MainActivity activity;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = MainActivity.getSharedInstance();
        GameScene scene = (GameScene) activity.mCurrentScene;
        int score = scene.points;

        ArrayList<Integer> list = new ArrayList<>();
        list.add(score);
        Collections.sort(list);
        System.out.println(list);
    }

}
