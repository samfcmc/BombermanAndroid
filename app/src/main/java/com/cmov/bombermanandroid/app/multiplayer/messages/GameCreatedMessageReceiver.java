package com.cmov.bombermanandroid.app.multiplayer.messages;

import com.cmov.bombermanandroid.app.multiplayer.MessageInterpreter;
import com.cmov.bombermanandroid.app.multiplayer.MultiplayerManager;

import org.json.JSONObject;

import java.io.ObjectOutputStream;

import pt.utl.ist.cmov.wifidirect.sockets.SimWifiP2pSocket;

/**
 * When a peer sends information about a game that it is hosting
 */
public class GameCreatedMessageReceiver extends MessageReceiver {
    @Override
    public void afterReceive(JSONObject json, ObjectOutputStream objectOutputStream) {
        MultiplayerManager.addMultiplayerGame(MessageInterpreter.getGameInfo(json.toString()));
    }
}
