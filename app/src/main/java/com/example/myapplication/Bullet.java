package com.example.myapplication;

import android.app.Activity;

import org.andengine.ui.activity.BaseActivity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.engine.camera.Camera;
import org.andengine.util.adt.color.Color;

public class Bullet {
    public Rectangle sprite;
    public Bullet() {
        sprite = new Rectangle(0, 0, 10, 10, MainActivity.getSharedInstance()
                .getVertexBufferObjectManager());

        sprite.setColor(Color.YELLOW);
    }
}
