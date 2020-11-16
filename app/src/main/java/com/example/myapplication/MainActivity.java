package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Vibrator;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.debug.Debug;

import java.io.IOException;


public class MainActivity extends SimpleBaseGameActivity {
    static final int CAMERA_WIDTH = 1920;
    static final int CAMERA_HEIGHT = 1080;
    private static final String SOUND_DIR = "mfx/";
    public static MainActivity instance;
    public Camera mCamera;
    public Font mFont;
    public Font mFont2;
    public Font mFont3;
    public Font mFont4;
    public Sound fire;
    public Sound lifeline;
    public Sound death;
    public Sound reload;
    public Sound lose;
    public Sound getready;
    public Scene mCurrentScene;
    Vibrator v;
    public SharedPreferences gamePrefs;
    public static final String GAME_PREFS = "ArithmeticFile";

    public static MainActivity getSharedInstance() {
        return instance;
    }

    public EngineOptions onCreateEngineOptions() {
        instance = this;
        mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        EngineOptions options = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), mCamera);
        options.getAudioOptions().setNeedsSound(true);
        options.getAudioOptions().setNeedsMusic(true);
        return options;
    }

    protected void onCreateResources() {
        mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 512, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 72, android.graphics.Color.rgb(255, 255, 51));
        mFont2 = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 512, Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), 52, Color.GREEN);
        mFont3 = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 512, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 72, android.graphics.Color.rgb(30, 144, 255));
        mFont4 = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 512, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 72, android.graphics.Color.rgb(255, 0, 0));
        mFont.load();
        mFont2.load();
        mFont3.load();
        mFont4.load();
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        gamePrefs = getSharedPreferences(GAME_PREFS, 0);
        try {
            fire = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), instance, SOUND_DIR + "laser.ogg");
            fire.setLoaded(true);
            death = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), instance, SOUND_DIR + "enemy_death.ogg");
            death.setLoaded(true);
            lifeline = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), instance, SOUND_DIR + "lifeline.ogg");
            lifeline.setLoaded(true);
            reload = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), instance, SOUND_DIR + "reload2.ogg");
            reload.setLoaded(true);
            lose = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), instance, SOUND_DIR + "lose2.ogg");
            lose.setLoaded(true);
            getready = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), instance, SOUND_DIR + "getready.ogg");
            getready.setLoaded(true);

        } catch (IOException e) {
            Debug.e("Cant find file");
        }
    }

    protected Scene onCreateScene() {
        mEngine.registerUpdateHandler(new FPSLogger());
        mCurrentScene = new SplashScene();
        return mCurrentScene;
    }

    public void setCurrentScene(Scene scene) {
        mCurrentScene = scene;
        getEngine().setScene(mCurrentScene);
    }


    public void setCurrentActivity() {
        startActivity(new Intent(MainActivity.this, MenuActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mCurrentScene instanceof GameScene) {
            ((GameScene) mCurrentScene).detach();
        }
        mCurrentScene = null;
        SensorListener.instance = null;
        ProximityListener.instance = null;
    }


    public void playFire() {
        if (fire != null) {
            fire.play();
        }
    }

    public void playLose() {
        if (lose != null) {
            lose.play();
        }
    }

    public void playReady() {
        if (getready != null) {
            getready.play();
        }
    }


    public void playLifeline() {
        if (lifeline != null) {
            lifeline.play();
        }
    }

    public void playDeath() {
        if (death != null) {
            death.play();
        }
    }

    public void playReload() {
        if (reload != null) {
            reload.play();
        }
    }

    public void vibrate() {
        v.vibrate(300);
    }

    public void vibrateLong() { v.vibrate(1000); }

}