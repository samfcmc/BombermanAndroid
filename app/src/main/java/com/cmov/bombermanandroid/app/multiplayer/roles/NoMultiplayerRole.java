package com.cmov.bombermanandroid.app.multiplayer.roles;

import com.cmov.bombermanandroid.app.multiplayer.messages.MessageFactory;

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

    @Override
    public int getLocalPlayer() {
        return -1;
    }
}
