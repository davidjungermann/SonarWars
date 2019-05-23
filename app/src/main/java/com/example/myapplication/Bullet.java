package com.example.myapplication;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.util.adt.color.Color;

public class Bullet {
    public Rectangle sprite;

    public Bullet() {
        sprite = new Rectangle(0, 0, 20, 20, MainActivity.getSharedInstance()
                .getVertexBufferObjectManager());
        sprite.setColor(Color.YELLOW);
    }
}
