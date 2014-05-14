package com.cmov.bombermanandroid.app.states;

import android.graphics.Canvas;

import com.cmov.bombermanandroid.app.Game;

/**
 *
 */
public class AllEnemiesAreDeadState extends State {

    public AllEnemiesAreDeadState() {
        super(false);
    }

    @Override
    public void update(Canvas canvas) {
        Game.startNextLevel();
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
