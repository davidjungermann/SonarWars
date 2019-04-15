package com.example.myapplication;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class ProximityListener implements SensorEventListener {
    static ProximityListener instance;
    GameScene scene;

    public static ProximityListener getSharedInstance() {
        if (instance == null)
            instance = new ProximityListener();
        return instance;
    }

    public ProximityListener() {
        instance = this;
        scene = (GameScene) MainActivity.getSharedInstance().mCurrentScene;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        synchronized (this) {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_PROXIMITY:
                    scene.proximity = Math.round(event.values[0]);
                    scene.bulletCount = 0;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
