package com.cmov.bombermanandroid.app.multiplayer.messages;

import com.cmov.bombermanandroid.app.multiplayer.FoundMultiplayerGameInfo;
import com.cmov.bombermanandroid.app.multiplayer.communication.CommunicationChannel;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class MessageInterpreter {

    private static Map<String, MessageReceiver> messagesReceivers =
            new HashMap<String, MessageReceiver>();

    private static JsonParser parser = new JsonParser();

    static {
        bootstrap();
    }

    private static void bootstrap() {
        messagesReceivers.put("game?", new AskGameMessageReceiver());
        messagesReceivers.put("game", new GameCreatedMessageReceiver());
        messagesReceivers.put("noGame", new NoMultiplayerGameMessageReceiver());
        messagesReceivers.put("join?", new AskJoinGameMessageReceiver());
        messagesReceivers.put("join", new JoinGameAcceptedMessageReceiver());
        messagesReceivers.put("update", new UpdateGameMessageReceiver());
        messagesReceivers.put("joinAck", new JoinAckMessageReceiver());
    }

    public static void interpretMessage(String message, CommunicationChannel communicationChannel) {
        JsonObject jsonMessage = parser.parse(message).getAsJsonObject();
        String type = jsonMessage.get("type").getAsString();
        MessageReceiver receiver = messagesReceivers.get(type);
        if (receiver != null) {
            receiver.afterReceive(jsonMessage, communicationChannel);
        }
    }

    public static FoundMultiplayerGameInfo getGameInfo(String message,
                                                       CommunicationChannel communicationChannel) {
        try {
            JSONObject jsonMessage = new JSONObject(message);
            jsonMessage.put("type", "game");
            String gameName = jsonMessage.getString("name");
            int maxPlayers = jsonMessage.getInt("maxPlayers");
            return new FoundMultiplayerGameInfo(gameName, maxPlayers, communicationChannel);
        } catch (JSONException e) {
            return null;
        }
    }
}
