package com.cmov.bombermanandroid.app.multiplayer;

import com.cmov.bombermanandroid.app.multiplayer.communication.CommunicationChannel;

/**
 *
 */
public class FoundMultiplayerGameInfo extends MultiplayerGameInfo {
    private CommunicationChannel communicationChannel;

    public FoundMultiplayerGameInfo(String name, int numberOfPlayers,
                                    CommunicationChannel communicationChannel) {
        super(name, numberOfPlayers);
        this.communicationChannel = communicationChannel;
    }

    public CommunicationChannel getCommunicationChannel() {
        return communicationChannel;
    }
}
