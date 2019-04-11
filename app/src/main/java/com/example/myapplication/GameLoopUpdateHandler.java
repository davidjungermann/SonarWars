package com.example.myapplication;

import org.andengine.engine.handler.IUpdateHandler;

public class GameLoopUpdateHandler implements IUpdateHandler {

    @Override
    public void onUpdate(float pSecondsElapsed) {
        ((GameScene) MainActivity.getSharedInstance().mCurrentScene).moveShip();
        ((GameScene) MainActivity.getSharedInstance().mCurrentScene).cleaner();
    }

    @Override
    public void reset() {

    }
}
