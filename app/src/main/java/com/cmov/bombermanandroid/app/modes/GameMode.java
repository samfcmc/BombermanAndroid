package com.cmov.bombermanandroid.app.modes;

import com.cmov.bombermanandroid.app.constants.Constants;

/**
 *
 */
public enum GameMode {

    SINGLEPLAYER(1, new SinglePlayerModeManager()), MULTIPLAYER(Constants.MAX_PLAYERS, new MultiplayerModeManager());

    private int maxPlayers;

    private ModeManager manager;

    private GameMode(int maxPlayers, ModeManager manager) {
        this.manager = manager;
        this.maxPlayers = maxPlayers;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public ModeManager getManager() {
        return manager;
    }
}
