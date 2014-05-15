package com.cmov.bombermanandroid.app.multiplayer.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;

import pt.utl.ist.cmov.wifidirect.sockets.SimWifiP2pSocket;

/**
 *
 */
public class WDSimCommunicationChannel implements CommunicationChannel {
    private SimWifiP2pSocket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public WDSimCommunicationChannel(SimWifiP2pSocket socket) {
        this.socket = socket;
        init();
    }

    private void init()
    {
        try
        {
            this.outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            this.outputStream.flush();
            this.inputStream = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
     }

    @Override
    public void sendMessage(String message) {
        try {
            this.outputStream.writeObject(message);
            this.outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String receiveMessage() {
        try {
            String response = (String) this.inputStream.readObject();
            return response;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void close() {
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
