package com.cmov.bombermanandroid.app.commands;

import com.cmov.bombermanandroid.app.Game;
import com.cmov.bombermanandroid.app.states.PausedState;
import com.cmov.bombermanandroid.app.states.RunningState;

/**
 *
 */
public class TurnPauseOnOffCommand extends Command{
    @Override
    public void execute() {
        if(Game.getCurrentState() instanceof PausedState) {
            Game.resumeTimeOutThread();
            Game.setCurrentState(new RunningState());
        }
        else {
            Game.stopTimeOutThread();
            Game.setCurrentState(new PausedState());
        }
    }
}
