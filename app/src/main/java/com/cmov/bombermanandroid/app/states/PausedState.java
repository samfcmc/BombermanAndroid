package com.cmov.bombermanandroid.app.states;

import android.graphics.Canvas;

import com.cmov.bombermanandroid.app.Game;

/**
 *
 */
public class PausedState extends State {
    public PausedState() {
        super(false);
    }

    @Override
    public void update(Canvas canvas) {
        Game.processCommands();
    }

    @Override
    public void draw(Canvas canvas) {
        Game.drawPauseText(canvas);
    }
}
