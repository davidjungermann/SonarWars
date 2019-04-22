package com.example.myapplication;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.BaseActivity;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.align.VerticalAlign;
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

import static java.lang.Thread.sleep;

public class GameScene extends Scene implements IOnSceneTouchListener {
    MainActivity activity;
    Camera mCamera;
    SensorManager sensorManager;

    public LinkedList<Bullet> bulletList;
    public Ship ship;
    private HUD hud;

    public int bulletCount;
    public int points = 0;
    int proximity;

    float accelerometerSpeedX;
    float accelerometerSpeedY;

    private Text pointsText;
    private Text magazine;
    public Text reloadWarning;




    public GameScene() {
        setOnSceneTouchListener(this);
        bulletList = new LinkedList<>();
        setBackground(new Background(Color.BLACK));
        mCamera = MainActivity.getSharedInstance().mCamera;
        ship = Ship.getSharedInstance();


        attachChild(ship.sprite);
        setHud();
        MainActivity.getSharedInstance().setCurrentScene(this);
        MainActivity.getSharedInstance().onCreateResources();



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
        ship.moveShip(accelerometerSpeedX, accelerometerSpeedY);
    }

    public void setHud() {
        hud = new HUD();
        mCamera.setHUD(hud);

        //Sets Magazine and ReloadWarning Text location and size
        magazine = new Text(50, 50, MainActivity.getSharedInstance().mFont, "00",
                MainActivity.getSharedInstance().getVertexBufferObjectManager());
        reloadWarning = new Text(960, 540, MainActivity.getSharedInstance().mFont, "abcdef",
                MainActivity.getSharedInstance().getVertexBufferObjectManager());
        //Add flashing effect
        LoopEntityModifier blinkRepeatedly =
                new LoopEntityModifier(new SequenceEntityModifier(new FadeOutModifier(1), new FadeInModifier(1)));
        reloadWarning.registerEntityModifier(blinkRepeatedly);

        reloadWarning.setScale(4);
        reloadWarning.setText("RELOAD");
        reloadWarning.setVisible(false);
        hud.attachChild(reloadWarning);
        hud.attachChild(magazine);

        //Sets Points
        pointsText = new Text(1800, 900, MainActivity.getSharedInstance().mFont, "000000",
                MainActivity.getSharedInstance().getVertexBufferObjectManager());
        hud.attachChild(pointsText);
    }

    public void updatePoints() {
        String pointsString = Integer.toString(points);
        pointsText.setText(pointsString);
    }


    public void updateMagazine() {
        int inMagazine = 20 - bulletCount;
        String magString = Integer.toString(inMagazine);
        magazine.setText(magString);
        if(Integer.parseInt(magString) <= 0) {
            reloadWarning.setVisible(true);
        }
    }


    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        if (!CoolDown.getSharedInstance().checkValidity()) {
            return false;
        }
        if (bulletCount < 20 && proximity == 5) {

            ship.shoot();
        }
        return true;
    }

    public void cleaner() {
        synchronized (this) {
            Iterator it = bulletList.iterator();
            while (it.hasNext()) {
                Bullet b = (Bullet) it.next();
                if (b.sprite.getY() > mCamera.getHeight()) {
                    BulletPool.sharedBulletPool().recyclePoolItem(b);
                    it.remove();
                    continue;
                }
            }
        }
    }
}

