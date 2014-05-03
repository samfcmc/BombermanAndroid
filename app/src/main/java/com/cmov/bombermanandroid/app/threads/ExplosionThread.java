package com.cmov.bombermanandroid.app.threads;

import com.cmov.bombermanandroid.app.model.Bomb;

import java.util.TimerTask;

public class ExplosionThread extends TimerTask {

    private Bomb bomb;

    public ExplosionThread(Bomb bomb) {
        this.bomb = bomb;
    }

    @Override
    public void run() {
        bomb.createExplosions();
        //now explosion take place
        bomb.setExplosionOccurred(true);
    }
}
