package com.cmov.bombermanandroid.app.multiplayer.messages;

import com.cmov.bombermanandroid.app.multiplayer.communication.CommunicationChannel;

import org.json.JSONObject;

public class AskJoinGameMessageReceiver implements MessageReceiver {
    @Override
    public void afterReceive(JSONObject json, CommunicationChannel communicationChannel) {
        String message = MessageFactory.createAskForJoinGameMessage();
        communicationChannel.sendMessage(message);
    }
}
