package com.cmov.bombermanandroid.app.multiplayer;

/**
 * MultiplayerGameInfo: A class to contain the necessary information
 * about a multiplayer game
 */
public class MultiplayerGameInfo {
    private String name;
    private int numberOfPlayers;

    public MultiplayerGameInfo(String name, int numberOfPlayers) {
        this.name = name;
        this.numberOfPlayers = numberOfPlayers;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
