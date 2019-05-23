package com.example.myapplication;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class ProximityListener implements SensorEventListener {
    static ProximityListener instance;
    GameScene scene;

    public ProximityListener() {
        instance = this;
        scene = (GameScene) MainActivity.getSharedInstance().mCurrentScene;
    }

    public static ProximityListener getSharedInstance() {
        if (instance == null)
            instance = new ProximityListener();
        return instance;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        synchronized (this) {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_PROXIMITY:
                    scene.proximity = Math.round(event.values[0]);
                    if (scene.bulletCount == 20) {
                        scene.bulletCount = 0;
                        MainActivity.getSharedInstance().playReload();
                    }
                    scene.reloadWarning.setVisible(false);
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
