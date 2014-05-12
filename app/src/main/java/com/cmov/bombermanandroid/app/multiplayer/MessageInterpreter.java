package com.cmov.bombermanandroid.app.multiplayer;

import com.cmov.bombermanandroid.app.multiplayer.messages.AskGameMessageReceiver;
import com.cmov.bombermanandroid.app.multiplayer.messages.GameCreatedMessageReceiver;
import com.cmov.bombermanandroid.app.multiplayer.messages.MessageReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import pt.utl.ist.cmov.wifidirect.sockets.SimWifiP2pSocket;

/**
 *
 */
public class MessageInterpreter {

    private static Map<String, MessageReceiver> messagesReceivers =
            new HashMap<String, MessageReceiver>();

    static {
        bootstrap();
    }

    private static void bootstrap() {
        messagesReceivers.put("game?", new AskGameMessageReceiver());
        messagesReceivers.put("game", new GameCreatedMessageReceiver());
    }

    public static void interpretMessage(String message, ObjectOutputStream outputStream) {
        try {
            JSONObject jsonMessage = new JSONObject(message);
            String type = jsonMessage.getString("type");
            MessageReceiver receiver = messagesReceivers.get(type);
            if(receiver != null) {
                receiver.afterReceive(jsonMessage, outputStream);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean peerHasGame(String message) {
        try {
            JSONObject jsonMessage = new JSONObject(message);
            if(jsonMessage.get("type").equals("gameInfo")) {
                return true;
            }
            else {
                return false;
            }
        } catch (JSONException e) {
            return false;
        }
    }

    public static MultiplayerGameInfo getGameInfo(String message) {
        try {
            JSONObject jsonMessage = new JSONObject(message);
            jsonMessage.put("type", "game");
            String gameName = jsonMessage.getString("name");
            int maxPlayers = jsonMessage.getInt("maxPlayers");
            return new MultiplayerGameInfo(gameName, maxPlayers);
        } catch (JSONException e) {
            return null;
        }
    }
}
