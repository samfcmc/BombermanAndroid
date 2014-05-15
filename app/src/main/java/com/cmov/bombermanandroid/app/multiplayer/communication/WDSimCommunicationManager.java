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
import com.cmov.bombermanandroid.app.constants.Constants;
import com.cmov.bombermanandroid.app.multiplayer.messages.MessageFactory;
import com.cmov.bombermanandroid.app.multiplayer.messages.MessageInterpreter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import android.os.Handler;
import java.util.logging.LogRecord;

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
public class WDSimCommunicationManager implements CommunicationManager {
    private static final int PORT = 10001;

    Activity activity;
    private SimWifiP2pManager manager;
    private SimWifiP2pManager.Channel channel;
    private Messenger service;
    private boolean bound = false;
    private WifiP2pServiceConnection connection = new WifiP2pServiceConnection();
    private Listener listener = new Listener();
    private SimWifiP2pSocketServer serverSocket;
    private MessageReceiverThread receiverThread = new MessageReceiverThread();

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
        wifiOn();
    }

    private void wifiOn() {
        Intent intent = new Intent(this.activity, SimWifiP2pService.class);
        boolean bind = this.activity.bindService(intent, this.connection, Context.BIND_AUTO_CREATE);
        this.bound = true;

        this.activity.runOnUiThread(new Thread() {
            @Override
            public void run() {
                receiverThread.start();
            }
        });

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

    private static class AskForGameThread extends Thread {

        private SimWifiP2pDeviceList peers;

        public AskForGameThread(SimWifiP2pDeviceList peers) {
            this.peers = peers;
        }

        @Override
        public void run() {
            for (SimWifiP2pDevice device : this.peers.getDeviceList()) {
                try {
                    Log.d("Peer found", device.getVirtIp());
                    SimWifiP2pSocket socket = new SimWifiP2pSocket(device.getVirtIp(), PORT);
                    WDSimCommunicationChannel channel = new WDSimCommunicationChannel(socket);
                    channel.sendMessage(MessageFactory.createAskForGameMessage());
                    new  CommunicationThread(channel).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private static class CommunicationThread extends Thread {
        private WDSimCommunicationChannel communicationChannel;

        public CommunicationThread(WDSimCommunicationChannel communicationChannel) {
            this.communicationChannel = communicationChannel;
        }

        @Override
        public void run() {
            String message = this.communicationChannel.receiveMessage();

            while (message != null || !message.equals(Constants.END_COMMUNICATION)) {
                MessageInterpreter.interpretMessage(message, this.communicationChannel);
                message = this.communicationChannel.receiveMessage();
            }
            this.communicationChannel.close();
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
                    WDSimCommunicationChannel channel = new WDSimCommunicationChannel(sock);
                    new CommunicationThread(channel).start();

                } catch (IOException e) {
                    Log.d("Error Accepting Socket", e.getMessage());
                    break;
                }
            }
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
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            WDSimCommunicationManager.this.service = null;
            WDSimCommunicationManager.this.manager = null;
            WDSimCommunicationManager.this.channel = null;
            WDSimCommunicationManager.this.bound = false;
        }
    }
}
