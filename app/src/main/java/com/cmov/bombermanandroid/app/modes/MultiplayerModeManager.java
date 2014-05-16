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
        Game.updateLocal(canvas);
    }

    @Override
    public void upPressed() {
        MultiplayerManager.getCurrentRole().upPressed();
    }

    @Override
    public void downPressed() {
        MultiplayerManager.getCurrentRole().downPressed();
    }

    @Override
    public void leftPressed() {
        MultiplayerManager.getCurrentRole().leftPressed();
    }

    @Override
    public void rightPressed() {
        MultiplayerManager.getCurrentRole().rightPressed();
    }

    @Override
    public void bombPressed() {
        MultiplayerManager.getCurrentRole().bombPressed();
    }

    @Override
    public void quitPressed() {

    }

    @Override
    public void updateEnemies() {
        MultiplayerManager.getCurrentRole().updateEnemies();
    }
}
