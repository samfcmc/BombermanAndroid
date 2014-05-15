package com.cmov.bombermanandroid.app.multiplayer.roles;

public abstract class MultiplayerRole {

    public abstract void notifyAboutUpdate();
    public abstract String createAskGameResponseMessage();
    public abstract int getLocalPlayer();
    public abstract void start();

}
