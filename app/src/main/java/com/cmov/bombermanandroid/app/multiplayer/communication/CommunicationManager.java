package com.cmov.bombermanandroid.app.multiplayer.communication;

import android.app.Activity;

/**
 * Interface for any class that handles communication.
 * The goal is to separate the multiplayer logic from communication
 * technology used to do it
 */
public interface CommunicationManager {
    public void init();
    public void destroy();
    public void requestPeers();
    public void sendMessage(CommunicationChannel communicationChannel, String message);
    public void receiveMessage(CommunicationChannel communicationChannel);
}
