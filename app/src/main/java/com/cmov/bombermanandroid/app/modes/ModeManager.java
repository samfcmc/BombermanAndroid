package com.cmov.bombermanandroid.app.modes;

import android.graphics.Canvas;

public abstract class ModeManager {

    public abstract void start();
    public abstract void update(Canvas canvas);
    public abstract void upPressed();
    public abstract void downPressed();
    public abstract void leftPressed();
    public abstract void rightPressed();
    public abstract void bombPressed();
    public abstract void quitPressed();
    public abstract void updateEnemies();
}
