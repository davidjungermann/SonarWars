package com.example.myapplication;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.BaseActivity;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.adt.color.Color;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.ui.activity.BaseActivity;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.adt.color.Color;

import java.util.Iterator;
import java.util.LinkedList;

public class GameScene extends Scene implements IOnSceneTouchListener {
    public LinkedList<Bullet> bulletList;
    public Ship ship;
    public int bulletCount;
    Camera mCamera;
    float accelerometerSpeedX;
    int proximity;
    SensorManager sensorManager;

    public GameScene() {
        setOnSceneTouchListener(this);
        bulletList = new LinkedList<>();
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
        ProximityListener.getSharedInstance();
        sensorManager.registerListener(ProximityListener.getSharedInstance(),
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_GAME);
        registerUpdateHandler(new GameLoopUpdateHandler());
    }

    public void moveShip() {
        ship.moveShip(accelerometerSpeedX);
    }

    public boolean proximity(){
        if(proximity < 5){
            ship.shoot();
        }
        return true;
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
            if(proximity < 5) {
                ship.shoot();
            }
        return true;
    }

    public void cleaner() {
        synchronized (this) {
            Iterator it = bulletList.iterator();
            while (it.hasNext()) {
                Bullet b = (Bullet) it.next();
                if (b.sprite.getY() <= -b.sprite.getHeight()) {
                    BulletPool.sharedBulletPool().recyclePoolItem(b);
                    it.remove();
                    continue;
                }
            }
        }
    }
}

