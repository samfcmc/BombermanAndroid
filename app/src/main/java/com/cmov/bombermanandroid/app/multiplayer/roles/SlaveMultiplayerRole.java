package com.cmov.bombermanandroid.app.multiplayer.roles;

import com.cmov.bombermanandroid.app.multiplayer.messages.MessageFactory;

public class SlaveMultiplayerRole extends MultiplayerRole {

    @Override
    public void notifyAboutUpdate() {

    }

    @Override
    public String createAskGameResponseMessage() {
        return MessageFactory.createNoMultiplayerGameCreatedMessage();
    }

    @Override
    public int getLocalPlayer() {
        //TODO: The master must give an id to the player
        return 0;
    }

}
