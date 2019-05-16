package com.example.myapplication;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

public class GameOverScene extends CameraScene implements IOnSceneTouchListener {
    boolean done;
    MainActivity activity;

    public GameOverScene(Camera mCamera){
        super(mCamera);
        activity = MainActivity.getSharedInstance();
        setBackgroundEnabled(false);
        GameScene scene = (GameScene) activity.mCurrentScene;

        Text result = new Text(0, 0, activity.mFont,
                activity.getString(R.string.gameover), MainActivity
                .getSharedInstance().getVertexBufferObjectManager());

        final int x = (int) (mCamera.getCenterX());
        final int y = (int) (mCamera.getCenterY());
        done = false;
        result.setPosition(x, mCamera.getHeight() + result.getHeight());
        MoveYModifier mod = new MoveYModifier(5, result.getY(), y) {
            @Override
            protected void onModifierFinished(IEntity pItem) {
                done = true;
            }
        };
        attachChild(result);
        result.registerEntityModifier(mod);
        setOnSceneTouchListener(this);
    }

    @Override
    public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
        if (!done)
            return true;
        ((GameScene) activity.mCurrentScene).resetValues();
        return false;
    }

}
