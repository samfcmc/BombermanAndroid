package com.cmov.bombermanandroid.app.threads;

import com.cmov.bombermanandroid.app.Game;
import com.cmov.bombermanandroid.app.events.TimePassedEvent;

import java.util.TimerTask;

/**
 *
 */
public class TimeOutThread extends TimerTask {
    /*
     * Duration until timeout (in seconds)
     */
    private int duration;
    private TimePassedEvent event;

    public TimeOutThread(int duration) {
        this.duration = duration;
        this.event = new TimePassedEvent(duration);
    }

    @Override
    public void run() {
        --this.duration;

        if(this.duration == 0) {
            Game.timeOut();
        }

        this.event.setTimeLeft(this.duration);
        Game.getEventBus().post(this.event);
    }

    public TimePassedEvent getEvent() {
        return event;
    }
}
