package com.cmov.bombermanandroid.app.multiplayer.communication;

import com.cmov.bombermanandroid.app.constants.Constants;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;

import pt.utl.ist.cmov.wifidirect.sockets.SimWifiP2pSocket;

/**
 *
 */
public class WDSimWithAddressCommunicationChannel implements CommunicationChannel {
    private String address;
    private SimWifiP2pSocket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public WDSimWithAddressCommunicationChannel(String address) {
        this.address = address;
        init();
    }
    

    private  void init(){
        try {
            openSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void openSocket() throws IOException {
        this.socket = new SimWifiP2pSocket(address, Constants.PORT);
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.outputStream.flush();
        this.inputStream = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void sendMessage(String message) {
        try {
            if (this.socket.isClosed()) {
                openSocket();
            }
            this.outputStream.writeObject(message);
            this.outputStream.flush();
            if(message.equals(Constants.END_COMMUNICATION)) {
                this.socket.close();
            }
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

    }
}
