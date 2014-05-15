package com.cmov.bombermanandroid.app.multiplayer.messages;

import com.cmov.bombermanandroid.app.constants.Constants;
import com.cmov.bombermanandroid.app.multiplayer.communication.CommunicationChannel;

import org.json.JSONObject;

/**
 *
 */
public class NoMultiplayerGameMessageReceiver implements MessageReceiver {
    @Override
    public void afterReceive(JSONObject json, CommunicationChannel communicationChannel) {
        communicationChannel.sendMessage(Constants.END_COMMUNICATION);
    }
}
