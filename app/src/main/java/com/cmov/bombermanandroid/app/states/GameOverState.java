package com.cmov.bombermanandroid.app.states;

import android.graphics.Canvas;

import com.cmov.bombermanandroid.app.Game;

/**
 *
 */
public class GameOverState extends State {
    public GameOverState() {
        super(false);
    }

    @Override
    public void update(Canvas canvas) {
        Game.stop();
    }

    @Override
    public void draw(Canvas canvas) {
        Game.drawGameOver(canvas);
    }
}
