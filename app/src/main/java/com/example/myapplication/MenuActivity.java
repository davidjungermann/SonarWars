package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);
        Button playGame = findViewById(R.id.button2);

        playGame.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(MenuActivity.this, MainActivity.class));
            }
        });
    }

    private void openGame(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
