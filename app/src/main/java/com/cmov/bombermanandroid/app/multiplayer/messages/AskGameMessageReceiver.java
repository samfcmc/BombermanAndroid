package com.cmov.bombermanandroid.app.multiplayer.messages;

import com.cmov.bombermanandroid.app.multiplayer.MultiplayerManager;

import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * AskGameMessageReceiver: When other peers ask "me"
 * if "I am" hosting a game
 */
public class AskGameMessageReceiver implements MessageReceiver  {
    @Override
    public void afterReceive(JSONObject json, ObjectOutputStream outputStream) {
        String response = MultiplayerManager.getCurrentRole().createAskGameResponseMessage();
        try {
            //ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(response);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
