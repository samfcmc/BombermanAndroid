package com.cmov.bombermanandroid.app.multiplayer.messages;

import com.cmov.bombermanandroid.app.constants.Constants;
import com.cmov.bombermanandroid.app.multiplayer.communication.CommunicationChannel;
import com.google.gson.JsonObject;

import org.json.JSONObject;

/**
 *
 */
public class NoMultiplayerGameMessageReceiver implements MessageReceiver {
    @Override
    public void afterReceive(JsonObject json, CommunicationChannel communicationChannel) {
        communicationChannel.sendMessage(Constants.END_COMMUNICATION);
    }
}
