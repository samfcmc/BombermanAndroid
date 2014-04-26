package com.cmov.bombermanandroid.app.threads;

import com.cmov.bombermanandroid.app.Game;

import java.util.TimerTask;

/**
 * Thread to generate commands for the enemies
 */
public class EnemiesThread extends TimerTask {
    public static final int INTERVAL = 25;

    @Override
    public void run() {
        Game.generateCommandForEnemies();
    }
}
