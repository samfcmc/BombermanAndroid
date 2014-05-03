package com.cmov.bombermanandroid.app.modes;

import com.cmov.bombermanandroid.app.constants.Constants;

/**
 *
 */
public enum GameMode {
    SINGLEPLAYER(1), MULTIPLAYER(Constants.MAX_PLAYERS);

    private int maxPlayers;

    private GameMode(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }
}
