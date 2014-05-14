package com.cmov.bombermanandroid.app.states;

import android.graphics.Canvas;

import com.cmov.bombermanandroid.app.Game;
import com.cmov.bombermanandroid.app.events.UpdatedGameStateEvent;

/**
 *
 */
public class RunningState extends State {
    public RunningState() {
        super(true);
    }

    @Override
    public void update(Canvas canvas) {
        Game.processCommands();
        Game.update(canvas);
        Game.getEventBus().post(new UpdatedGameStateEvent());
    }

    @Override
    public void draw(Canvas canvas) {
        Game.drawGrid(canvas);
    }
}
