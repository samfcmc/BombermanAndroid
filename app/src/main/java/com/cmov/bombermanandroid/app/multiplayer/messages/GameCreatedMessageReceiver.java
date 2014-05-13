package com.cmov.bombermanandroid.app.multiplayer.messages;

import com.cmov.bombermanandroid.app.multiplayer.MultiplayerManager;

import org.json.JSONObject;

import java.io.ObjectOutputStream;

/**
 * When a peer sends information about a game that it is hosting
 */
public class GameCreatedMessageReceiver implements MessageReceiver {
    @Override
    public void afterReceive(JSONObject json, ObjectOutputStream objectOutputStream) {
        MultiplayerManager.addMultiplayerGame(MessageInterpreter.getGameInfo(json.toString()));
    }
}
