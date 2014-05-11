package com.cmov.bombermanandroid.app.multiplayer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

import com.cmov.bombermanandroid.app.SimWifiP2pBroadcastReceiver;

import pt.utl.ist.cmov.wifidirect.SimWifiP2pBroadcast;
import pt.utl.ist.cmov.wifidirect.sockets.SimWifiP2pSocketManager;

/**
 * MultiplayerManager: This class handles all the multiplayer behavior and
 * act as a wrapper around the WifiDirect API or any simulator of it
 */
public class MultiplayerManager {

    public static void init(Activity activity) {
        SimWifiP2pSocketManager.Init(activity.getApplicationContext());
        IntentFilter filter = new IntentFilter();
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_STATE_CHANGED_ACTION);
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_PEERS_CHANGED_ACTION);
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_NETWORK_MEMBERSHIP_CHANGED_ACTION);
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_GROUP_OWNERSHIP_CHANGED_ACTION);
        SimWifiP2pBroadcastReceiver receiver = new SimWifiP2pBroadcastReceiver(activity);
        activity.registerReceiver(receiver, filter);
    }

}
