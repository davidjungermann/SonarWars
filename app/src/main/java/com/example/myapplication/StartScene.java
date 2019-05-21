package com.example.myapplication;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.scene.Scene;;
import org.andengine.entity.text.Text;
import org.andengine.util.modifier.IModifier;

public class StartScene extends Scene {
    MainActivity activity;
    final int MENU_START = 0;

    public StartScene() {
        activity = MainActivity.getSharedInstance();
        Text getReady = new Text(0, 0, activity.mFont, activity.getString(R.string.getready), activity.getVertexBufferObjectManager());
        getReady.setPosition(0, activity.mCamera.getCenterY());
        attachChild(getReady);
        getReady.registerEntityModifier(new MoveXModifier(1, getReady.getX(), activity.mCamera.getCenterX()));

        loadResources();

    }

    public void loadResources(){
        DelayModifier dMod = new DelayModifier(3, new IEntityModifier.IEntityModifierListener() {
            @Override
            public void onModifierStarted(IModifier arg0, IEntity arg1) {
            }

            public void onModifierFinished(IModifier arg0, IEntity arg1) {
                activity.setCurrentScene(new GameScene());
            }
        });
        registerEntityModifier(dMod);
    }
}
