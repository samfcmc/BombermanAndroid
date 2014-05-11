package com.cmov.bombermanandroid.app.multiplayer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;

import com.cmov.bombermanandroid.app.SimWifiP2pBroadcastReceiver;
import com.cmov.bombermanandroid.app.constants.Constants;

import pt.utl.ist.cmov.wifidirect.SimWifiP2pBroadcast;
import pt.utl.ist.cmov.wifidirect.SimWifiP2pDeviceList;
import pt.utl.ist.cmov.wifidirect.SimWifiP2pInfo;
import pt.utl.ist.cmov.wifidirect.SimWifiP2pManager;
import pt.utl.ist.cmov.wifidirect.service.SimWifiP2pService;
import pt.utl.ist.cmov.wifidirect.sockets.SimWifiP2pSocketManager;

/**
 * MultiplayerManager: This class handles all the multiplayer behavior and
 * act as a wrapper around the WifiDirect API or any simulator of it
 */
public class MultiplayerManager implements SimWifiP2pManager.PeerListListener,
        SimWifiP2pManager.GroupInfoListener{
    private static Activity activity;

    private static SimWifiP2pManager manager;
    private static SimWifiP2pManager.Channel channel;
    private static Messenger service;
    private static boolean bound = false;
    private static WifiP2pServiceConnection connection = new WifiP2pServiceConnection();

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
    }

    public static void wifiOn() {
        Intent intent = new Intent(activity, SimWifiP2pService.class);
        activity.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        bound = true;
    }

    public static void wifiOff() {
        if(bound) {
            activity.unbindService(connection);
            bound = false;
        }
    }

    @Override
    public void onGroupInfoAvailable(SimWifiP2pDeviceList simWifiP2pDeviceList, SimWifiP2pInfo simWifiP2pInfo) {

    }

    @Override
    public void onPeersAvailable(SimWifiP2pDeviceList simWifiP2pDeviceList) {

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
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            MultiplayerManager.service = null;
            MultiplayerManager.manager = null;
            MultiplayerManager.channel = null;
            MultiplayerManager.bound = false;
        }
    }

}
