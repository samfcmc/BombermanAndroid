package com.cmov.bombermanandroid.app.modes;

import android.graphics.Canvas;

import com.cmov.bombermanandroid.app.Game;
import com.cmov.bombermanandroid.app.multiplayer.MultiplayerManager;

public class MultiplayerModeManager extends ModeManager {

    @Override
    public void start() {
        MultiplayerManager.getCurrentRole().start();
    }

    @Override
    public void update(Canvas canvas) {

    }

    @Override
    public void upPressed() {

    }

    @Override
    public void downPressed() {

    }

    @Override
    public void leftPressed() {

    }

    @Override
    public void rightPressed() {

    }

    @Override
    public void bombPressed() {

    }

    @Override
    public void quitPressed() {

    }

    @Override
    public void updateEnemies() {
        MultiplayerManager.getCurrentRole().updateEnemies();
    }
}
