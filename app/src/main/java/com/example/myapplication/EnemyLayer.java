package com.example.myapplication;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class EnemyLayer extends Entity {

    private LinkedList<Enemy> enemies;
    public static EnemyLayer instance;
    public int enemyCount;
    public Camera mCamera;
    public TimerHandler time;
    public int width;

    public static EnemyLayer getSharedInstance() {
        return instance;
    }

    public static boolean isEmpty() {
        if (instance.enemies.size() == 0)
            return true;
        return false;
    }

    public static Iterator getIterator() {
        return instance.enemies.iterator();
    }

    public EnemyLayer(int x) {
        enemies = new LinkedList();
        instance = this;
        enemyCount = x;
        restart();
    }

    public void restart(){
        enemies.clear();
        clearEntityModifiers();
        clearUpdateHandlers();
        mCamera = MainActivity.getSharedInstance().mCamera;

        for(int i = 0; i < enemyCount; i++){
            registerUpdateHandler(time = new TimerHandler(1, new ITimerCallback() {

                @Override
                public void onTimePassed(TimerHandler pTimerHandler) {
                    Random rand = new Random();
                    width = (int) mCamera.getWidth();
                    Enemy e = (Enemy) EnemyPool.sharedEnemyPool().obtainPoolItem();
                    e.sprite.setPosition(rand.nextInt(width), -20);
                    e.sprite.setVisible(true);
                    attachChild(e.sprite);
                    enemies.add(e);
                    setVisible(true);
                    MoveYModifier moveDown = new MoveYModifier(10, mCamera.getHeight(), -20);
                    e.sprite.registerEntityModifier(new LoopEntityModifier(moveDown));
                }
            }));

        }
    }
    public void purge() {
        detachChildren();
        for (Enemy e : enemies) {
            EnemyPool.sharedEnemyPool().recyclePoolItem(e);
        }
        enemies.clear();
    }

    public static void purgeAndRestart() {
        instance.purge();
        instance.restart();
    }

    @Override
    public void onDetached() {
        purge();
        super.onDetached();
    }
}
