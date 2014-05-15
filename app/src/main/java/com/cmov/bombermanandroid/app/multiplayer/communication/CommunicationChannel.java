package com.cmov.bombermanandroid.app.multiplayer.communication;

/**
 *
 */
public interface CommunicationChannel {
    public abstract void sendMessage(String message);
    public abstract String receiveMessage();
    public abstract void close();
}
