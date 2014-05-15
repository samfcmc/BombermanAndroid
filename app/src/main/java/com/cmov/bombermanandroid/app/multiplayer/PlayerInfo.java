package com.cmov.bombermanandroid.app.multiplayer;

/**
 *
 */
public class PlayerInfo {

    private int playerNumber;
    private int x;
    private int y;

    public PlayerInfo(int playerNumber, int x, int y) {
        this.playerNumber = playerNumber;
        this.x = x;
        this.y = y;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
