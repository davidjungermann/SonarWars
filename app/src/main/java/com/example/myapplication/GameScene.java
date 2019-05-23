package com.example.myapplication;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.IEntityFactory;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.particle.ParticleSystem;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.RotationParticleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.color.Color;

import java.util.Iterator;
import java.util.LinkedList;

import static org.andengine.util.adt.color.Color.YELLOW;

public class GameScene extends Scene implements IOnSceneTouchListener {
    public LinkedList<Bullet> bulletList;
    public Ship ship;
    public int bulletCount;
    public int points = 0;
    public Text reloadWarning;
    public Text bombText;
    public MainActivity activity;
    Camera mCamera;
    SensorManager sensorManager;
    float proximity;
    float accelerometerSpeedX;
    float accelerometerSpeedY;
    int bombCounter = 3;
    private HUD hud;
    private Text pointsText;
    private Text magazine;
    public PointParticleEmitter particleEmitter;


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
        bombText = new Text(50, magazine.getHeight() + 50, MainActivity.getSharedInstance().mFont3, "00",
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

    public void updateBomb() {
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
            MainActivity.getSharedInstance().playFire();
        }
        return true;
    }

    public void cleaner() {
        synchronized (this) {
            Iterator<Enemy> eIt = EnemySpawn.getIterator();
            while (eIt.hasNext()) {
                Enemy e = eIt.next();

                if (e.sprite.getY() < 0) {
                    MainActivity.getSharedInstance().vibrate();
                    MainActivity.getSharedInstance().playLose();
                    detach();
                    SensorListener.instance = null;
                    ProximityListener.instance = null;
                    setChildScene(new GameOverScene());
                }
                if (accelerometerSpeedY < -5 && bombCounter > 0) {
                    MainActivity.getSharedInstance().playLifeline();
                    EnemySpawn.getSharedInstance().purge();
                    MainActivity.getSharedInstance().vibrate();
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
                            createExplosion(e.sprite.getX(), e.sprite.getY(), e.sprite.getParent(), MainActivity.getSharedInstance());

                            EnemyPool.sharedEnemyPool().recyclePoolItem(e);
                            MainActivity.getSharedInstance().playDeath();
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

    public void createExplosion(final float posX, final float posY, final IEntity target, final SimpleBaseGameActivity activity) {
        int mNumPart = 15;
        int mTimePart = 2;

        particleEmitter = new PointParticleEmitter(posX, posY);
        IEntityFactory recFact = new IEntityFactory() {
            @Override
            public Rectangle create(float pX, float pY) {
                Rectangle rect = new Rectangle(posX, posY, 20, 20, activity.getVertexBufferObjectManager());
                rect.setColor(YELLOW);
                return rect;
            }
        };

        final ParticleSystem<Rectangle> particleSystem = new ParticleSystem<Rectangle>(
                recFact, particleEmitter, 500, 500, mNumPart);

        particleSystem
                .addParticleInitializer(new VelocityParticleInitializer<Rectangle>(
                        -50, 50, -50, 50));

        particleSystem
                .addParticleModifier(new AlphaParticleModifier<Rectangle>(0,
                        0.6f * mTimePart, 1, 0));
        particleSystem
                .addParticleModifier(new RotationParticleModifier<Rectangle>(0,
                        mTimePart, 0, 360));

        target.attachChild(particleSystem);
        target.registerUpdateHandler(new TimerHandler(mTimePart,
                new ITimerCallback() {
                    @Override
                    public void onTimePassed(final TimerHandler pTimerHandler) {
                        particleSystem.detachSelf();
                        target.sortChildren();
                        target.unregisterUpdateHandler(pTimerHandler);
                    }
                }));

    }

}