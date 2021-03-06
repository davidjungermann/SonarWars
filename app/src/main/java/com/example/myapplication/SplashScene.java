package com.example.myapplication;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;
import org.andengine.util.modifier.IModifier;

public class SplashScene extends Scene {
    MainActivity activity;

    public SplashScene() {
        setBackground(new Background(Color.BLACK));
        activity = MainActivity.getSharedInstance();
        Text title1 = new Text(0, 0, activity.mFont, activity.getString(R.string.title_1), activity.getVertexBufferObjectManager());
        Text title2 = new Text(0, 0, activity.mFont, activity.getString(R.string.title_2), activity.getVertexBufferObjectManager());

        title1.setPosition(-title1.getWidth(), activity.mCamera.getCenterY());
        title2.setPosition(activity.mCamera.getWidth(), activity.mCamera.getCenterY());

        attachChild(title1);
        attachChild(title2);

        title1.registerEntityModifier(new MoveXModifier(1, title1.getX(), activity.mCamera.getCenterX() - title2.getWidth() + 90));
        title2.registerEntityModifier(new MoveXModifier(1, title2.getX(), activity.mCamera.getCenterX() + title1.getWidth() - 90));
        loadResources();
        MainActivity.getSharedInstance().playReady();

    }

    public void loadResources() {
        DelayModifier dMod = new DelayModifier(2, new IEntityModifier.IEntityModifierListener() {
            @Override
            public void onModifierStarted(IModifier arg0, IEntity arg1) {
            }

            public void onModifierFinished(IModifier arg0, IEntity arg1) {
                activity.setCurrentScene(new StartScene());
                MainActivity.getSharedInstance().playReady();
            }
        });
        registerEntityModifier(dMod);
    }
}
