package com.cmov.bombermanandroid.app.multiplayer.messages;

import com.cmov.bombermanandroid.app.multiplayer.communication.CommunicationChannel;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateGameMessageReceiver implements MessageReceiver {

    private static Map<String, MessageReceiver> updateReceivers =
        new HashMap<String, MessageReceiver>();

    static {
        bootstrap();
    }

    private static void bootstrap(){
        updateReceivers.put("move", new MoveUpdateGameMessageReceiver());
        updateReceivers.put("bomb", new BombUpdateGameMessageReceiver());
    }

   @Override
    public void afterReceive(JsonObject json, CommunicationChannel communicationChannel) {
       String type = json.get("type").getAsString();
       MessageReceiver receiver = updateReceivers.get(type);

       if(receiver != null){
           receiver.afterReceive(json, communicationChannel);
       }
    }
 }
