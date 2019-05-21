package com.example.myapplication;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.util.adt.color.Color;


public class Enemy {

    public Rectangle sprite;
    public int hp;
    //the max health for each enemy
    protected final int MAX_HEALTH = 1;

    public Enemy() {
        sprite = new Rectangle(0, 0, 80, 80, MainActivity.getSharedInstance().getVertexBufferObjectManager());
        sprite.setColor(Color.RED);
        init();
    }

    // method for initializing the Enemy object , used by the constructor and
// the EnemyPool class
    public void init() {
        hp = MAX_HEALTH;
        sprite.registerEntityModifier(new LoopEntityModifier(
                new RotationModifier(5, 0, 360)));
    }

    public void clean() {
        sprite.clearEntityModifiers();
        sprite.clearUpdateHandlers();
    }

    // method for applying hit and checking if enemy died or not
// returns true if enemy died
    public boolean gotHit() {
        synchronized (this) {
            hp--;
            if (hp <= 0)
                return true;
            else
                return false;
        }
    }
}
