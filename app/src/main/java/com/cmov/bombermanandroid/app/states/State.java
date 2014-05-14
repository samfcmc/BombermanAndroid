package com.cmov.bombermanandroid.app.states;

import android.graphics.Canvas;

/**
 *
 */
public abstract class State {
    private boolean canDropBomb;

    protected State(boolean canDropBomb) {
        this.canDropBomb = canDropBomb;
    }

    public boolean isCanDropBomb() {
        return canDropBomb;
    }

    public abstract void update(Canvas canvas);
    public abstract void draw(Canvas canvas);
}
