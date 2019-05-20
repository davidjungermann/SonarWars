package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class HighscoreActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        GameScene gc = new GameScene();
        final TextView score = (TextView)findViewById(R.id.textView8);
        score.setText(gc.points);
    }

}
