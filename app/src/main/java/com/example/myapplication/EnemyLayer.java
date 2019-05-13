package com.example.myapplication;

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

        for (int i = 0; i < enemyCount; i++) {
            Enemy e = (Enemy) EnemyPool.sharedEnemyPool().obtainPoolItem();
            float finalPosX = (i % 6) * 4 * e.sprite.getWidth();
            float finalPosY = ((int) (i / 6)) * e.sprite.getHeight() * 2;

            Random r = new Random();
            e.sprite.setPosition(r.nextInt(2) == 0 ? -e.sprite.getWidth() * 3
                            : MainActivity.CAMERA_WIDTH + e.sprite.getWidth() * 3,
                    (r.nextInt(5) + 1) * e.sprite.getHeight());
            e.sprite.setVisible(true);

            attachChild(e.sprite);
            e.sprite.registerEntityModifier(new MoveModifier(2,
                    e.sprite.getX(), finalPosX, e.sprite.getY(), finalPosY));

            enemies.add(e);

            setVisible(true);
            setPosition(50, 30);

            MoveXModifier movRight = new MoveXModifier(1, 50, 120);
            MoveXModifier movLeft = new MoveXModifier(1, 120, 50);
            MoveYModifier moveDown = new MoveYModifier(1, 30, 100);
            MoveYModifier moveUp = new MoveYModifier(1, 100, 30);

            registerEntityModifier(new LoopEntityModifier(
                    new SequenceEntityModifier(movRight, moveDown, movLeft, moveUp)));
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
