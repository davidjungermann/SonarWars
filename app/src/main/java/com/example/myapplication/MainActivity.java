package com.example.myapplication;

import android.graphics.Color;
import android.graphics.Typeface;

import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.resolutionpolicy.FixedResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import java.io.IOException;


public class MainActivity extends SimpleBaseGameActivity {
    static final int CAMERA_WIDTH = 1920;
    static final int CAMERA_HEIGHT = 1080;
    public Camera mCamera;
    public Font mFont;
    public Font mFont2;
    public Font mFont3;
    public Sound fire;

    //A reference to the current scene
    public Scene mCurrentScene;
    public static MainActivity instance;
    private static final String SOUND_DIR = "mfx/";

    public EngineOptions onCreateEngineOptions() {
        instance = this;
        mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FixedResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
    }

    protected void onCreateResources() {
        mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 512, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 72, android.graphics.Color.rgb(255, 255, 51));
        mFont2 = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 512, Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), 52, Color.GREEN);
        mFont3 = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 512, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 72, Color.BLUE);
        mFont.load();
        mFont2.load();
        mFont3.load();
        try {
            fire = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), instance, SOUND_DIR + "laser.ogg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        fire.setLoaded(true);
    }

    protected Scene onCreateScene() {
        mEngine.registerUpdateHandler(new FPSLogger());
        mCurrentScene = new SplashScene();
        return mCurrentScene;
    }

    public static MainActivity getSharedInstance() {
        return instance;
    }

    public void setCurrentScene(Scene scene) {
        mCurrentScene = scene;
        getEngine().setScene(mCurrentScene);
    }

    @Override
    public void onBackPressed() {
        if (mCurrentScene instanceof GameScene)
            ((GameScene) mCurrentScene).detach();

        mCurrentScene = null;
        SensorListener.instance = null;
        ProximityListener.instance = null;
        super.onBackPressed();
    }

}