package com.cmov.bombermanandroid.app.multiplayer.communication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;

import com.cmov.bombermanandroid.app.SimWifiP2pBroadcastReceiver;
import com.cmov.bombermanandroid.app.multiplayer.MessageFactory;
import com.cmov.bombermanandroid.app.multiplayer.MessageInterpreter;
import com.cmov.bombermanandroid.app.multiplayer.MultiplayerGameInfo;
import com.cmov.bombermanandroid.app.multiplayer.MultiplayerManager;
import com.cmov.bombermanandroid.app.multiplayer.NoMultiplayerRole;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import pt.utl.ist.cmov.wifidirect.SimWifiP2pBroadcast;
import pt.utl.ist.cmov.wifidirect.SimWifiP2pDevice;
import pt.utl.ist.cmov.wifidirect.SimWifiP2pDeviceList;
import pt.utl.ist.cmov.wifidirect.SimWifiP2pInfo;
import pt.utl.ist.cmov.wifidirect.SimWifiP2pManager;
import pt.utl.ist.cmov.wifidirect.service.SimWifiP2pService;
import pt.utl.ist.cmov.wifidirect.sockets.SimWifiP2pSocket;
import pt.utl.ist.cmov.wifidirect.sockets.SimWifiP2pSocketManager;
import pt.utl.ist.cmov.wifidirect.sockets.SimWifiP2pSocketServer;

/**
 * Handles communication using WDSIM
 */
public class WDSimCommunicationManager implements CommunicationManager{
    Activity activity;

    private SimWifiP2pManager manager;
    private SimWifiP2pManager.Channel channel;
    private Messenger service;
    private boolean bound = false;
    private WifiP2pServiceConnection connection = new WifiP2pServiceConnection();
    private Listener listener = new Listener();
    private SimWifiP2pSocketServer serverSocket;
    private static final int PORT = 10001;

    public WDSimCommunicationManager(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void init() {
        SimWifiP2pSocketManager.Init(this.activity.getApplicationContext());
        IntentFilter filter = new IntentFilter();
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_STATE_CHANGED_ACTION);
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_PEERS_CHANGED_ACTION);
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_NETWORK_MEMBERSHIP_CHANGED_ACTION);
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_GROUP_OWNERSHIP_CHANGED_ACTION);
        SimWifiP2pBroadcastReceiver receiver = new SimWifiP2pBroadcastReceiver(activity);
        this.activity.registerReceiver(receiver, filter);
        //MultiplayerManager.foundGames = new ArrayList<MultiplayerGameInfo>();
        //MultiplayerManager.currentRole = new NoMultiplayerRole();
        wifiOn();
    }

    private void wifiOn() {
        Intent intent = new Intent(this.activity, SimWifiP2pService.class);
        boolean bind = this.activity.bindService(intent, this.connection, Context.BIND_AUTO_CREATE);
        this.bound = true;
    }

    @Override
    public void destroy() {
        wifiOff();
    }

    @Override
    public void requestPeers() {
        this.manager.requestPeers(this.channel,
                this.listener);
    }

    private void wifiOff() {
        if (this.bound) {
            this.activity.unbindService(this.connection);
            this.bound = false;
        }
    }

    protected static void sendMessage(SimWifiP2pDevice device, String message) {
        SimWifiP2pSocket clientSocket = null;
        try {
            clientSocket = new SimWifiP2pSocket(device.getVirtIp(), PORT);
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            outputStream.writeObject(message);
            outputStream.flush();
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
            String response = (String) inputStream.readObject();
            MessageInterpreter.interpretMessage(response, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static class Listener implements SimWifiP2pManager.PeerListListener,
            SimWifiP2pManager.GroupInfoListener {

        @Override
        public void onGroupInfoAvailable(SimWifiP2pDeviceList peers, SimWifiP2pInfo simWifiP2pInfo) {

        }

        @Override
        public void onPeersAvailable(SimWifiP2pDeviceList peers) {
            new AskForGameThread(peers).start();
        }
    }

    private class WifiP2pServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            WDSimCommunicationManager.this.service = new Messenger(service);
            WDSimCommunicationManager.this.manager = new SimWifiP2pManager(
                    WDSimCommunicationManager.this.service);
            WDSimCommunicationManager.this.channel = WDSimCommunicationManager.this.
                    manager.initialize(WDSimCommunicationManager.this.activity.getApplication(),
                    WDSimCommunicationManager.this.activity.getMainLooper(),
                    null);
            new MessageReceiverThread().start();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            WDSimCommunicationManager.this.service = null;
            WDSimCommunicationManager.this.manager = null;
            WDSimCommunicationManager.this.channel = null;
            WDSimCommunicationManager.this.bound = false;
        }
    }

    private static class AskForGameThread extends Thread {

        private SimWifiP2pDeviceList peers;

        public AskForGameThread(SimWifiP2pDeviceList peers) {
            this.peers = peers;
        }

        @Override
        public void run() {
            for (SimWifiP2pDevice device : this.peers.getDeviceList()) {
                sendMessage(device, MessageFactory.createAskForGameMessage());
            }
        }
    }

    private static class MessageReceiverThread extends Thread {

        private SimWifiP2pSocketServer mServerSocket = null;

        @Override
        public void run() {
            try {
                this.mServerSocket = new SimWifiP2pSocketServer(PORT);

            } catch (IOException e) {
                e.printStackTrace();
            }
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    SimWifiP2pSocket sock = this.mServerSocket.accept();
                    //Toast.makeText(activity, "Socket accepted", Toast.LENGTH_LONG).show();

                    ObjectOutputStream outputStream = new ObjectOutputStream(sock.getOutputStream());
                    outputStream.flush();
                    // reads the message
                    ObjectInputStream inputStream = new ObjectInputStream(sock.getInputStream());
                    String message = (String) inputStream.readObject();
                    // Pass to a message interpreter to interpret the message
                    MessageInterpreter.interpretMessage(message, outputStream);
                    sock.close();
                } catch (IOException e) {
                    Log.d("Error Accepting Socket", e.getMessage());
                    break;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
