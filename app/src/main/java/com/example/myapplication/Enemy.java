package com.example.myapplication;

import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.util.adt.color.Color;

import java.util.Random;


public class Enemy {

    //the max health for each enemy
    public Rectangle sprite;
    private int MAX_HEALTH;
    private Color color;
    public int hp;

    public Enemy() {
        sprite = new Rectangle(0, 0, 80, 80, MainActivity.getSharedInstance().getVertexBufferObjectManager());
        if(new Random().nextInt(100)>=50) {
             MAX_HEALTH = 2;
             color = Color.BLUE;
        } else {
             MAX_HEALTH = 1;
             color = Color.RED;
        }
    }

    // method for initializing the Enemy object , used by the constructor and
// the EnemyPool class
    public void init() {
        hp = MAX_HEALTH;
        sprite.setColor(color);
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
            return hp <= 0;
        }
    }
}
