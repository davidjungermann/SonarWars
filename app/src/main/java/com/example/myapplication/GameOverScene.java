package com.example.myapplication;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;

public class GameOverScene extends MenuScene implements MenuScene.IOnMenuItemClickListener {
    MainActivity activity;
    final int MENU_START = 0;
    String score;

    public GameOverScene() {
        super(MainActivity.getSharedInstance().mCamera);
        activity = MainActivity.getSharedInstance();
        setBackground(new Background(Color.BLACK));
        GameScene scene = (GameScene) activity.mCurrentScene;
        int score = scene.points;
        IMenuItem startButton = new TextMenuItem(MENU_START, activity.mFont, "Game over!", activity.getVertexBufferObjectManager());
        IMenuItem replayButton = new TextMenuItem(MENU_START, activity.mFont, "Replay", activity.getVertexBufferObjectManager());
        Text result = new Text(0, 0, activity.mFont,
                activity.getString(R.string.score) + " "
                        + score, MainActivity
                .getSharedInstance().getVertexBufferObjectManager());
        result.setPosition(mCamera.getCenterX(), mCamera.getCenterY() + startButton.getHeight());
        startButton.setPosition(mCamera.getCenterX(), mCamera.getCenterY());
        replayButton.setPosition(mCamera.getCenterX(), mCamera.getCenterY() - startButton.getHeight());
        addMenuItem(startButton);
        addMenuItem(replayButton);
        attachChild(result);
        setOnMenuItemClickListener(this);
        MainActivity.getSharedInstance().playGameOver();
    }

    public boolean onMenuItemClicked(MenuScene arg0, IMenuItem arg1, float arg2, float arg3) {
        switch (arg1.getID()) {
            case MENU_START:
                MainActivity.getSharedInstance().pauseGameOver();
                activity.setCurrentScene(new GameScene());
            default:
                break;
        }
        return false;
    }
}
