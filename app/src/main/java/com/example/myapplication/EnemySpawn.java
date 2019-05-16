package com.example.myapplication;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.util.modifier.IModifier;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import static java.lang.Thread.sleep;

public class EnemySpawn extends Entity {

    private LinkedList<Enemy> enemies;
    public static EnemySpawn instance;
    public int enemyCount;
    public Camera mCamera;
    public TimerHandler time;
    public int width;

    public static EnemySpawn getSharedInstance() {
        return instance;
    }

    public static Iterator getIterator() {
        return instance.enemies.iterator();
    }

    public EnemySpawn() {
        enemies = new LinkedList();
        instance = this;
        spawn();
    }

    public void spawn() {
        mCamera = MainActivity.getSharedInstance().mCamera;
        width = (int) mCamera.getWidth();

        TimerHandler spriteMoveHandler = new TimerHandler(1, true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                Random rand = new Random();

                Enemy e = (Enemy) EnemyPool.sharedEnemyPool().obtainPoolItem();
                e.sprite.setPosition(rand.nextInt(width), -20);
                e.sprite.setVisible(true);
                attachChild(e.sprite);
                enemies.add(e);
                setVisible(true);
                MoveYModifier moveDown = new MoveYModifier(5, mCamera.getHeight(), -50);
                enemies.clear();
                e.sprite.registerEntityModifier(moveDown);

            }
        });
        registerUpdateHandler(spriteMoveHandler);
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
        instance.spawn();
    }

    @Override
    public void onDetached() {
        purge();
        super.onDetached();
    }
}
