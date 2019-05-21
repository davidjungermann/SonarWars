package com.example.myapplication;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.adt.color.Color;
import java.util.Iterator;
import java.util.LinkedList;

public class GameScene extends Scene implements IOnSceneTouchListener {
    Camera mCamera;
    SensorManager sensorManager;

    public LinkedList<Bullet> bulletList;
    public Ship ship;
    private HUD hud;

    public int bulletCount;
    public int points = 0;
    float proximity;

    float accelerometerSpeedX;
    float accelerometerSpeedY;
    int bombCounter = 3;

    private Text pointsText;
    private Text magazine;
    public Text reloadWarning;
    public Text bombText;


    public GameScene() {
        setOnSceneTouchListener(this);
        bulletList = new LinkedList();
        setBackground(new Background(Color.BLACK));
        attachChild(new EnemySpawn());
        mCamera = MainActivity.getSharedInstance().mCamera;
        ship = Ship.getSharedInstance();
        attachChild(ship.sprite);
        setHud();
        resetValues();
        MainActivity.getSharedInstance().setCurrentScene(this);
        MainActivity.getSharedInstance().onCreateResources();
        sensorManager = (SensorManager) MainActivity.getSharedInstance()
                .getSystemService(BaseGameActivity.SENSOR_SERVICE);
        ProximityListener.getSharedInstance();
        sensorManager.registerListener(ProximityListener.getSharedInstance(),
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_GAME);
        SensorListener.getSharedInstance();
        sensorManager.registerListener(SensorListener.getSharedInstance(),
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);
    }

    public void moveShip() {
        ship.moveShip(3 * accelerometerSpeedX);
    }

    public void setHud() {
        hud = new HUD();
        mCamera.setHUD(hud);

        //Sets Magazine and ReloadWarning Text location and size
        magazine = new Text(50, 50, MainActivity.getSharedInstance().mFont, "00",
                MainActivity.getSharedInstance().getVertexBufferObjectManager());
        reloadWarning = new Text(mCamera.getCenterX(), mCamera.getCenterY(), MainActivity.getSharedInstance().mFont, "abcdef",
                MainActivity.getSharedInstance().getVertexBufferObjectManager());
        //Add flashing effect
        LoopEntityModifier blinkRepeatedly =
                new LoopEntityModifier(new SequenceEntityModifier(new FadeOutModifier(1), new FadeInModifier(1)));
        reloadWarning.registerEntityModifier(blinkRepeatedly);

        reloadWarning.setScale(2);
        reloadWarning.setText("RELOAD");
        reloadWarning.setVisible(false);
        hud.attachChild(reloadWarning);
        hud.attachChild(magazine);

        //Sets bomb counter
        bombText = new Text(mCamera.getWidth() - 50, 50, MainActivity.getSharedInstance().mFont3, "00",
                MainActivity.getSharedInstance().getVertexBufferObjectManager());
        hud.attachChild(bombText);

        //Sets Points
        pointsText = new Text(50, mCamera.getHeight() - 50, MainActivity.getSharedInstance().mFont2, "000000",
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
        if (Integer.parseInt(magString) <= 0) {
            reloadWarning.setVisible(true);
        }
    }

    public void updateBomb(){
        String bombString = Integer.toString(bombCounter);
        bombText.setText(bombString);
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        if (!CoolDown.getSharedInstance().checkValidity()) {
            return false;
        }
        if (bulletCount < 20) {
            ship.shoot();
        }
        return true;
    }

    public void cleaner() {
        synchronized (this) {
            Iterator<Enemy> eIt = EnemySpawn.getIterator();
            while (eIt.hasNext()) {
                Enemy e = eIt.next();

                if (e.sprite.getY() < 0) {
                    //Här borde det bli Game Over
                }
                if(accelerometerSpeedY < -8 && bombCounter > 0){
                    EnemySpawn.getSharedInstance().purge();
                    bombCounter--;
                }
                Iterator<Bullet> it = bulletList.iterator();
                while (it.hasNext()) {
                    Bullet b = it.next();
                    if (b.sprite.getY() >= mCamera.getHeight()) {
                        BulletPool.sharedBulletPool().recyclePoolItem(b);
                        it.remove();
                        continue;
                    }
                    if (b.sprite.collidesWith(e.sprite)) {
                        if (e.gotHit()) {
                            EnemyPool.sharedEnemyPool().recyclePoolItem(e);
                            eIt.remove();
                            points = points + 10;
                        }
                        BulletPool.sharedBulletPool().recyclePoolItem(b);
                        it.remove();
                        break;
                    }

                }
            }

        }
    }

    public void resetValues() {
        bulletCount = 0;
        points = 0;
        ship.restart();
        clearChildScene();
        EnemySpawn.purgeAndSpawn();
        registerUpdateHandler(new GameLoopUpdateHandler());
    }

    public void detach() {
        Log.v("SonarWars", "GameScene onDetached()");
        clearUpdateHandlers();
        for (Bullet b : bulletList) {
            BulletPool.sharedBulletPool().recyclePoolItem(b);
        }
        bulletList.clear();
        detachChildren();
        Ship.instance = null;
        EnemyPool.instance = null;
        BulletPool.instance = null;
    }

}



