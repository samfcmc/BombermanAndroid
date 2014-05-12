package com.cmov.bombermanandroid.app.multiplayer;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 */
public class MessageInterpreter {
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
            String gameName = jsonMessage.getString("name");
            int maxPlayers = jsonMessage.getInt("maxPlayers");
            return new MultiplayerGameInfo(gameName, maxPlayers);
        } catch (JSONException e) {
            return null;
        }
    }
}
