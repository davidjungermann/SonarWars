package com.example.myapplication;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.ui.activity.BaseActivity;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.adt.color.Color;

public class GameScene extends Scene {
    public Ship ship;
    Camera mCamera;
    float accelerometerSpeedX;
    SensorManager sensorManager;

    public GameScene() {
        setBackground(new Background(Color.BLACK));
        mCamera = MainActivity.getSharedInstance().mCamera;
        ship = Ship.getSharedInstance();
        attachChild(ship.sprite);
        MainActivity.getSharedInstance().setCurrentScene(this);
        sensorManager = (SensorManager) MainActivity.getSharedInstance()
                .getSystemService(BaseGameActivity.SENSOR_SERVICE);
        SensorListener.getSharedInstance();
        sensorManager.registerListener(SensorListener.getSharedInstance(),
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);
        registerUpdateHandler(new GameLoopUpdateHandler());
    }

    public void moveShip() {
        ship.moveShip(accelerometerSpeedX);
    }
}
