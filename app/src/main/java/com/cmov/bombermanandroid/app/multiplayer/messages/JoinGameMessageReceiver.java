package com.cmov.bombermanandroid.app.multiplayer.messages;

import com.cmov.bombermanandroid.app.Game;
import com.cmov.bombermanandroid.app.multiplayer.communication.CommunicationChannel;

import org.json.JSONObject;

public class JoinGameMessageReceiver implements MessageReceiver {
    @Override
    public void afterReceive(JSONObject json, CommunicationChannel communicationChannel) {

        if(Game.hasFreeSlots()){
            String response = MessageFactory.createJoinGameMessage(Game.getNextFreeSlot());
            communicationChannel.sendMessage(response);
        }
        else {
            String response = MessageFactory.createJoinRefuseGameMessage();
            communicationChannel.sendMessage(response);
        }
    }
}
