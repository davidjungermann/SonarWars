package com.example.myapplication;

import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;

public class GameOverScene extends MenuScene implements MenuScene.IOnMenuItemClickListener {
    MainActivity activity;
    final int REPLAY_START = 0;
    final int MENU_START = 1;
    MenuActivity menu;

    public GameOverScene() {
        super(MainActivity.getSharedInstance().mCamera);
        activity = MainActivity.getSharedInstance();
        setBackground(new Background(Color.BLACK));
        GameScene scene = (GameScene) activity.mCurrentScene;
        int score = scene.points;
        IMenuItem replayButton = new TextMenuItem(REPLAY_START, activity.mFont, "Replay", activity.getVertexBufferObjectManager());
        IMenuItem menuButton = new TextMenuItem(MENU_START, activity.mFont, "Menu", activity.getVertexBufferObjectManager());

        Text result = new Text(0, 0, activity.mFont,
                activity.getString(R.string.score) + " "
                        + score, MainActivity
                .getSharedInstance().getVertexBufferObjectManager());

        Text gameOver = new Text(0, 0, activity.mFont,
                activity.getString(R.string.gameover), MainActivity
                .getSharedInstance().getVertexBufferObjectManager());

        gameOver.setPosition(activity.mCamera.getCenterX(), activity.mCamera.getHeight());
        replayButton.setPosition(mCamera.getCenterX() + replayButton.getWidth(), mCamera.getCenterY());
        menuButton.setPosition(mCamera.getCenterX() - replayButton.getWidth(), mCamera.getCenterY());
        attachChild(gameOver);
        gameOver.registerEntityModifier(new MoveYModifier(1, gameOver.getY(), activity.mCamera.getHeight() - 100));
        result.setPosition(mCamera.getCenterX(), activity.mCamera.getHeight() - (100 + gameOver.getHeight()));
        attachChild(result);
        addMenuItem(replayButton);
        addMenuItem(menuButton);
        setOnMenuItemClickListener(this);
    }

    public boolean onMenuItemClicked(MenuScene arg0, IMenuItem arg1, float arg2, float arg3) {
        switch (arg1.getID()) {
            case REPLAY_START:
                activity.setCurrentScene(new GameScene());
            default:
                break;
            case MENU_START:
                break;
        }
        return false;
    }

}
