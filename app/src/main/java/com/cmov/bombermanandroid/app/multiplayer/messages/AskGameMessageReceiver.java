package com.cmov.bombermanandroid.app.multiplayer.messages;

import com.cmov.bombermanandroid.app.multiplayer.MultiplayerManager;
import com.cmov.bombermanandroid.app.multiplayer.communication.CommunicationChannel;
import com.google.gson.JsonObject;

/**
 * AskGameMessageReceiver: When other peers ask "me"
 * if "I am" hosting a game
 */
public class AskGameMessageReceiver implements MessageReceiver {
    @Override
    public void afterReceive(JsonObject json, CommunicationChannel communicationChannel) {
        String response = MultiplayerManager.getCurrentRole().createAskGameResponseMessage();
        communicationChannel.sendMessage(response);
    }
}
