package com.cmov.bombermanandroid.app.multiplayer;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

import com.cmov.bombermanandroid.app.Game;
import com.cmov.bombermanandroid.app.SimWifiP2pBroadcastReceiver;
import com.cmov.bombermanandroid.app.events.MultiplayerGameFoundEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

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
 * MultiplayerManager: This class handles all the multiplayer behavior and
 * act as a wrapper around the WifiDirect API or any simulator of it
 */
public class MultiplayerManager {
    private static Activity activity;

    private static SimWifiP2pManager manager;
    private static SimWifiP2pManager.Channel channel;
    private static Messenger service;
    private static boolean bound = false;
    private static WifiP2pServiceConnection connection = new WifiP2pServiceConnection();
    private static Listener listener = new Listener();
    private static SimWifiP2pSocketServer serverSocket;
    private static List<MultiplayerGameInfo> foundGames;
    private static final int PORT = 10001;

    public static void init(Activity activity) {
        SimWifiP2pSocketManager.Init(activity.getApplicationContext());
        IntentFilter filter = new IntentFilter();
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_STATE_CHANGED_ACTION);
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_PEERS_CHANGED_ACTION);
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_NETWORK_MEMBERSHIP_CHANGED_ACTION);
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_GROUP_OWNERSHIP_CHANGED_ACTION);
        SimWifiP2pBroadcastReceiver receiver = new SimWifiP2pBroadcastReceiver(activity);
        activity.registerReceiver(receiver, filter);
        MultiplayerManager.activity = activity;
        MultiplayerManager.foundGames = new ArrayList<MultiplayerGameInfo>();
        wifiOn();
    }

    public static List<MultiplayerGameInfo> getFoundGames() {
        return foundGames;
    }

    public static void wifiOn() {
        Intent intent = new Intent(activity, SimWifiP2pService.class);
        boolean bind = activity.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        bound = true;
    }

    public static void wifiOff() {
        if (bound) {
            activity.unbindService(connection);
            bound = false;
        }
    }


    public static void requestPeers() {
        MultiplayerManager.manager.requestPeers(MultiplayerManager.channel,
                MultiplayerManager.listener);
        try {
            serverSocket = new SimWifiP2pSocketServer(PORT);
        } catch (IOException e) {
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
            Toast.makeText(activity, "Peers " + peers.getDeviceList().size(), Toast.LENGTH_LONG).show();
            for(SimWifiP2pDevice device : peers.getDeviceList()) {
                new AskForGameAsyncTask().execute(device.getVirtIp());
            }
        }
    }

    private static class WifiP2pServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MultiplayerManager.service = new Messenger(service);
            MultiplayerManager.manager = new SimWifiP2pManager(MultiplayerManager.service);
            MultiplayerManager.channel = MultiplayerManager.manager.initialize(
                    MultiplayerManager.activity.getApplication(),
                    MultiplayerManager.activity.getMainLooper(),
                    null);
            MultiplayerManager.bound = true;
            new MessageReceiverAsyncTask().execute();
            MultiplayerManager.manager.requestPeers(MultiplayerManager.channel,
                    MultiplayerManager.listener);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            MultiplayerManager.service = null;
            MultiplayerManager.manager = null;
            MultiplayerManager.channel = null;
            MultiplayerManager.bound = false;
        }
    }

    private static class AskForGameAsyncTask extends AsyncTask<String, Void, MultiplayerGameInfo> {

        @Override
        protected MultiplayerGameInfo doInBackground(String... params) {
            try {
                SimWifiP2pSocket clientSocket = new SimWifiP2pSocket(params[0], PORT);
                OutputStream outputStream = clientSocket.getOutputStream();
                outputStream.write(MessageFactory.createAskForGameMessage().getBytes());
                outputStream.flush();
            } catch (UnknownHostException e) {
            } catch (IOException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(MultiplayerGameInfo multiplayerGameInfo) {
            if(multiplayerGameInfo != null) {
                foundGames.add(multiplayerGameInfo);
                Game.getEventBus().post(new MultiplayerGameFoundEvent());
            }
        }
    }

    private static class MessageReceiverAsyncTask extends AsyncTask<Void, SimWifiP2pSocketServer, Void> {

        private final String TAG = "MultiplayerActivity";
        private SimWifiP2pSocketServer mServerSocket = null;

        @Override
        protected Void doInBackground(Void... voids) {

            Log.d(TAG, "Incomming Message Task started ...");

            try {
                this.mServerSocket = new SimWifiP2pSocketServer(PORT);

            } catch (IOException e) {
                e.printStackTrace();
            }
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    SimWifiP2pSocket sock = this.mServerSocket.accept();
                    Log.d(TAG, "Closing accepted socket because mCliSocket still active.");
                    // reads the message
                    BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                    String message = br.readLine();
                    // Pass to a message interpreter to interpret the message
                    sock.close();
                } catch (IOException e) {
                    Log.d("Error Accepting Socket", e.getMessage());
                    break;
                }
            }
            return null;
        }
    }
}
