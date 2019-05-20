package com.example.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.TextureRegion;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);
        Button playGame = findViewById(R.id.button2);
        Button tutorial = (Button)findViewById(R.id.button3);

        final MediaPlayer music = MediaPlayer.create(MenuActivity.this,R.raw.themesong);
        music.start();
        music.setLooping(true);

        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, TutorialActivity.class));
            }
        });

        playGame.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(MenuActivity.this, MainActivity.class));
                music.stop();

            }
        });
    }
}
