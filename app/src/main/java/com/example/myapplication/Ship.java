package com.example.myapplication;

import android.app.Activity;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.ui.activity.BaseActivity;

public class Ship {
    public Rectangle sprite;
    public static Ship instance;
    Camera mCamera;
    private boolean moveable;

    public static Ship getSharedInstance() {
        if (instance == null)
            instance = new Ship();
        return instance;
    }

    private Ship() {
        sprite = new Rectangle(0, 0, 280, 120, MainActivity.getSharedInstance()
                .getVertexBufferObjectManager());
        mCamera = MainActivity.getSharedInstance().mCamera;
        sprite.setPosition(mCamera.getCenterX(), 20);
        moveable = true;
    }

    public void moveShip(float accelerometerSpeedX, float accelerometerSpeedY) {
        if (!moveable)
            return;

        if (accelerometerSpeedX != 0 || accelerometerSpeedY != 0) {
            int lL = 0;
            int rL = (int) mCamera.getWidth();
            float newX;

            // Calculate New X,Y Coordinates within Limits
            if (sprite.getX() >= lL) {
                newX = sprite.getX() + accelerometerSpeedX * 5;
            } else {
                newX = lL;
            }
            if (newX <= rL) {
                newX = sprite.getX() + accelerometerSpeedX * 5;
            } else {
                newX = rL;
            }

            // Double Check That New X,Y Coordinates are within Limits
            if (newX < lL)
                newX = lL;
            else if (newX > rL)
                newX = rL;
            sprite.setPosition(newX, sprite.getY());
        }
    }

    public void shoot() {
        if (!moveable) {
            return;
        }
        GameScene scene = (GameScene) MainActivity.getSharedInstance().mCurrentScene;
        Bullet b = BulletPool.sharedBulletPool().obtainPoolItem();
        b.sprite.setPosition(sprite.getX(), sprite.getY() + 50);
        MoveYModifier mod = new MoveYModifier(1.5f, b.sprite.getY(),
                mCamera.getHeight());
        b.sprite.setVisible(true);
        b.sprite.detachSelf();
        scene.attachChild(b.sprite);
        scene.bulletList.add(b);
        b.sprite.registerEntityModifier(mod);
        scene.bulletCount++;
    }

    public void restart() {
        moveable = false;
        Camera mCamera = MainActivity.getSharedInstance().mCamera;
        MoveXModifier mod = new MoveXModifier(0.2f, sprite.getX(),
                mCamera.getWidth() / 2 - sprite.getWidth() / 2) {
            @Override
            protected void onModifierFinished(IEntity pItem) {
                super.onModifierFinished(pItem);
                moveable = true;
            }
        };
        sprite.registerEntityModifier(mod);
    }
}
