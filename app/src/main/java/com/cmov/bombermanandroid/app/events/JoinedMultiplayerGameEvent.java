package com.cmov.bombermanandroid.app.events;

import com.cmov.bombermanandroid.app.multiplayer.FoundMultiplayerGameInfo;

/**
 *
 */
public class JoinedMultiplayerGameEvent  {
    private FoundMultiplayerGameInfo multiplayerGameInfo;

    public JoinedMultiplayerGameEvent(FoundMultiplayerGameInfo multiplayerGameInfo) {
        this.multiplayerGameInfo = multiplayerGameInfo;
    }

    public FoundMultiplayerGameInfo getMultiplayerGameInfo() {
        return multiplayerGameInfo;
    }
}
