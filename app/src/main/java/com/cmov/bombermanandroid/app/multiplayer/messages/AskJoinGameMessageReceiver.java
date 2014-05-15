package com.cmov.bombermanandroid.app.multiplayer.messages;

import com.cmov.bombermanandroid.app.Game;
import com.cmov.bombermanandroid.app.multiplayer.MultiplayerManager;
import com.cmov.bombermanandroid.app.multiplayer.communication.CommunicationChannel;
import com.google.gson.JsonObject;

import org.json.JSONObject;

public class AskJoinGameMessageReceiver implements MessageReceiver {
    @Override
    public void afterReceive(JsonObject json, CommunicationChannel communicationChannel) {
        if(Game.hasFreeSlots()){
            String response = MessageFactory.createJoinGameMessage(Game.getPlayers(),
                    MultiplayerManager.getCurrentHostedGame().getName(),
                    Game.getNextFreeSlot());
            communicationChannel.sendMessage(response);
        }
        else {
            String response = MessageFactory.createJoinRefuseGameMessage();
            communicationChannel.sendMessage(response);
        }
    }
}
