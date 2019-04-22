package com.example.myapplication;

import org.andengine.engine.handler.IUpdateHandler;

public class GameLoopUpdateHandler implements IUpdateHandler {

    @Override
    public void onUpdate(float pSecondsElapsed) {
        ((GameScene) MainActivity.getSharedInstance().mCurrentScene).moveShip();
        ((GameScene) MainActivity.getSharedInstance().mCurrentScene).cleaner();
        ((GameScene) MainActivity.getSharedInstance().mCurrentScene).updateMagazine();
        ((GameScene) MainActivity.getSharedInstance().mCurrentScene).updatePoints();
    }

    @Override
    public void reset() {

    }
}
