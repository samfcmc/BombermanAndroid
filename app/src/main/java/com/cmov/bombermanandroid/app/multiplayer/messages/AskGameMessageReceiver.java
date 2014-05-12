package com.cmov.bombermanandroid.app.multiplayer.messages;

import com.cmov.bombermanandroid.app.multiplayer.MessageFactory;
import com.cmov.bombermanandroid.app.multiplayer.MultiplayerGameInfo;

import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectOutputStream;

import pt.utl.ist.cmov.wifidirect.sockets.SimWifiP2pSocket;

/**
 * AskGameMessageReceiver: When other peers ask "me"
 * if "I am" hosting a game
 */
public class AskGameMessageReceiver extends MessageReceiver  {
    @Override
    public void afterReceive(JSONObject json, ObjectOutputStream outputStream) {
        //TODO: Send the right response!!
        String response = MessageFactory.createMultiplayerGameCreatedMessage(
                new MultiplayerGameInfo("Test", 5));
        try {
            //ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(response);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
