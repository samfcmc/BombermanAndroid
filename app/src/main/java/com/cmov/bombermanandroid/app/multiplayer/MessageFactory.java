package com.cmov.bombermanandroid.app.multiplayer;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 */
public class MessageFactory {

    public static String createAskForGameMessage() {
        JSONObject json = new JSONObject();
        try {
            json.put("type", "game?");
            return  json.toString();
        } catch (JSONException e) {
            return null;
        }
    }

    public static String gameCreated(MultiplayerGameInfo gameInfo) {
        JSONObject json = new JSONObject();
        try {
            json.put("name", gameInfo.getName());
            json.put("maxPlayers", gameInfo.getNumberOfPlayers());
            return  json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
