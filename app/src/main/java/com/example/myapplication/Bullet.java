package com.example.myapplication;

import android.app.Activity;

import org.andengine.ui.activity.BaseActivity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.engine.camera.Camera;

public class Bullet {
    public Rectangle sprite;
    public Bullet() {
        sprite = new Rectangle(0, 0, 10, 10, MainActivity.getSharedInstance()
                .getVertexBufferObjectManager());

        sprite.setColor(0.09904f, 0f, 0.1786f);
    }
}
