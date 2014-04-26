package com.cmov.bombermanandroid.app.commands;

import com.cmov.bombermanandroid.app.Game;

/**
 *
 */
public class TurnPauseOnOffCommand extends Command{
    @Override
    public void execute() {
        Game.setPaused(!Game.isPaused());
    }
}
