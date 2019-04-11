package com.example.myapplication;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;

public class Ship {
    public Rectangle sprite;
    public static Ship instance;
    Camera mCamera;
    boolean moveable;

    public static Ship getSharedInstance() {
        if (instance == null)
            instance = new Ship();
        return instance;
    }

    private Ship() {
        sprite = new Rectangle(0, 0, 270, 180, MainActivity.getSharedInstance()
                .getVertexBufferObjectManager());
        mCamera = MainActivity.getSharedInstance().mCamera;
        sprite.setPosition(mCamera.getWidth() / 2 - sprite.getWidth() / 2,
                mCamera.getHeight() / 2 - sprite.getHeight() / 2);
        moveable = true;
    }

    public void moveShip(float accelerometerSpeedX) {
        if (!moveable)
            return;

        if (accelerometerSpeedX != 0) {
            int lL = 0;
            int rL = (int) (mCamera.getWidth() - (int) sprite.getWidth());
            float newX;

            // Calculate New X,Y Coordinates within Limits
            if (sprite.getX() >= lL)
                newX = sprite.getX() + accelerometerSpeedX;
            else
                newX = lL;
            if (newX <= rL)
                newX = sprite.getX() + accelerometerSpeedX;
            else
                newX = rL;

            // Double Check That New X,Y Coordinates are within Limits
            if (newX < lL)
                newX = lL;
            else if (newX > rL)
                newX = rL;
            sprite.setPosition(newX, sprite.getY());
        }
    }
}
