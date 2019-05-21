package com.example.myapplication;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.util.adt.color.Color;

public class GameOverScene extends MenuScene implements MenuScene.IOnMenuItemClickListener {
    MainActivity activity;
    final int MENU_START = 0;

    public GameOverScene() {
        super(MainActivity.getSharedInstance().mCamera);
        activity = MainActivity.getSharedInstance();

        setBackground(new Background(Color.BLACK));
        IMenuItem startButton = new TextMenuItem(MENU_START, activity.mFont, "Game over!", activity.getVertexBufferObjectManager());
        startButton.setPosition(mCamera.getCenterX(), mCamera.getCenterY());
        addMenuItem(startButton);

        setOnMenuItemClickListener(this);
    }

    public boolean onMenuItemClicked(MenuScene arg0, IMenuItem arg1, float arg2, float arg3) {
        switch (arg1.getID()) {
            case MENU_START:
                activity.setCurrentScene(new GameScene());
            default:
                break;
        }
        return false;
    }
}
