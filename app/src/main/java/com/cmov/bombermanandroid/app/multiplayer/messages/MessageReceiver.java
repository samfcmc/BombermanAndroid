package com.cmov.bombermanandroid.app.multiplayer.messages;

import com.cmov.bombermanandroid.app.multiplayer.communication.CommunicationChannel;
import com.google.gson.JsonObject;

/**
 * This class represents the behavior associated with each
 * received message
 */
public interface MessageReceiver {
    public void afterReceive(JsonObject json, CommunicationChannel communicationChannel);
}
