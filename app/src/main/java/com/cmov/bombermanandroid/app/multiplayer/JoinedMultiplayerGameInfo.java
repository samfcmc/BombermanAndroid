package com.cmov.bombermanandroid.app.multiplayer;

import com.cmov.bombermanandroid.app.multiplayer.communication.CommunicationChannel;

import java.util.List;

/**
 *
 */
public class JoinedMultiplayerGameInfo extends FoundMultiplayerGameInfo {
    private int playerNumber;
    private int level;
    private List<PlayerInfo> players;

    public JoinedMultiplayerGameInfo(String name, int numberOfPlayers,
                                     CommunicationChannel communicationChannel,
                                     int playerNumber, int level, List<PlayerInfo> players) {
        super(name, numberOfPlayers, communicationChannel);
        this.playerNumber = playerNumber;
        this.level = level;
        this.players = players;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getLevel() {
        return level;
    }

    public List<PlayerInfo> getPlayers() {
        return players;
    }
}
