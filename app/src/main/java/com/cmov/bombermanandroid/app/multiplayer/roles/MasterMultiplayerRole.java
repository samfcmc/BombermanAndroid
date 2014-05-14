package com.cmov.bombermanandroid.app.multiplayer.roles;

import com.cmov.bombermanandroid.app.multiplayer.MultiplayerManager;
import com.cmov.bombermanandroid.app.multiplayer.messages.MessageFactory;

public class MasterMultiplayerRole extends MultiplayerRole {

    private static final int LOCAL_PLAYER = 1;

    @Override
    public void notifyAboutUpdate() {

    }

    @Override
    public String createAskGameResponseMessage() {
        return MessageFactory.createMultiplayerGameCreatedMessage(
                MultiplayerManager.getCurrentHostedGame());
    }

    @Override
    public int getLocalPlayer() {
        return LOCAL_PLAYER;
    }
}
