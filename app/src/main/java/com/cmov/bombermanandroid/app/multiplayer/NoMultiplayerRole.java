package com.cmov.bombermanandroid.app.multiplayer;

/**
 * When the peer didn't host a game neither
 * joined one
 */
public class NoMultiplayerRole extends MultiplayerRole {
    @Override
    public void notifyAboutUpdate() {

    }

    @Override
    public String createAskGameResponseMessage() {
        return MessageFactory.createNoMultiplayerGameCreatedMessage();
    }
}
