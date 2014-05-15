package com.cmov.bombermanandroid.app.multiplayer.messages;

import com.cmov.bombermanandroid.app.multiplayer.MultiplayerGameInfo;

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

    public static String createMultiplayerGameCreatedMessage(MultiplayerGameInfo gameInfo) {
        JSONObject json = new JSONObject();
        try {
            json.put("type", "game");
            json.put("name", gameInfo.getName());
            json.put("maxPlayers", gameInfo.getNumberOfPlayers());
            return  json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String createNoMultiplayerGameCreatedMessage() {
        JSONObject json = new JSONObject();
        try {
            json.put("type", "noGame");
            return json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String createJoinGameMessage(){
       JSONObject json = new JSONObject();
        try {
            json.put("type", "join");
            // TODO : update the message with the game state
            return json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String createAskForJoinGameMessage(){
        JSONObject json = new JSONObject();
        try {
            json.put("type", "join?");
            return json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String createUpdateGameMessage(){
        JSONObject json = new JSONObject();
        try {
            json.put("type", "update");
            // TODO : update the message with the game updates
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
