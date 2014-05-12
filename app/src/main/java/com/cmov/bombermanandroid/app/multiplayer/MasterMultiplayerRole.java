package com.cmov.bombermanandroid.app.multiplayer;

public class MasterMultiplayerRole extends MultiplayerRole {

    @Override
    public void notifyAboutUpdate() {

    }

    @Override
    public String createAskGameResponseMessage() {
        return MessageFactory.createMultiplayerGameCreatedMessage(
                MultiplayerManager.getCurrentHostedGame());
    }
}
