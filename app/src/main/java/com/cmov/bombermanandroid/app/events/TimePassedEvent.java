package com.cmov.bombermanandroid.app.events;

/**
 *
 */
public class TimePassedEvent {
    /*
     * Time left in seconds
     */
    private int timeLeft;

    public TimePassedEvent(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }
}
