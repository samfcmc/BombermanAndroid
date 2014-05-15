package com.cmov.bombermanandroid.app.multiplayer.messages;

import com.cmov.bombermanandroid.app.constants.Constants;
import com.cmov.bombermanandroid.app.multiplayer.MultiplayerManager;
import com.cmov.bombermanandroid.app.multiplayer.communication.CommunicationChannel;
import com.google.gson.JsonObject;

/**
 * When a peer sends information about a game that it is hosting
 */
public class GameCreatedMessageReceiver implements MessageReceiver {
    @Override
    public void afterReceive(JsonObject json, CommunicationChannel communicationChannel) {
        MultiplayerManager.addMultiplayerGame(MessageInterpreter.getGameInfo(json.toString(),
                communicationChannel));
        communicationChannel.sendMessage(Constants.END_COMMUNICATION);
    }
}
