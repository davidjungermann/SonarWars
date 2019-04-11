package com.example.myapplication;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.DisplayMetrics;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;

public class MainActivity extends SimpleBaseGameActivity {
    static int CAMERA_WIDTH ;
    static int CAMERA_HEIGHT;
    public Camera mCamera;
    public Font mFont;

    //A reference to the current scene
    public Scene mCurrentScene;
    public static MainActivity instance;

    public EngineOptions onCreateEngineOptions() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        CAMERA_HEIGHT = metrics.widthPixels;
        CAMERA_WIDTH = metrics.heightPixels;
        instance = this;
        mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
    }

    protected void onCreateResources() {
        mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.MONOSPACE, Typeface.BOLD), 32, android.graphics.Color.rgb(255, 255, 51));
        mFont.load();
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
}