package com.cmov.bombermanandroid.app.multiplayer;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

import com.cmov.bombermanandroid.app.Game;
import com.cmov.bombermanandroid.app.SimWifiP2pBroadcastReceiver;
import com.cmov.bombermanandroid.app.events.MultiplayerGameFoundEvent;
import com.cmov.bombermanandroid.app.multiplayer.communication.CommunicationManager;
import com.cmov.bombermanandroid.app.multiplayer.messages.MessageReceiver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    private static final int PORT = 10001;
    private static List<MultiplayerGameInfo> foundGames;
    private static MultiplayerGameInfo currentHostedGame;
    private static MultiplayerRole currentRole;
    private static CommunicationManager communicationManager;

    public static void init(CommunicationManager communicationManager) {
        MultiplayerManager.foundGames = new ArrayList<MultiplayerGameInfo>();
        MultiplayerManager.currentRole = new NoMultiplayerRole();
        MultiplayerManager.communicationManager = communicationManager;
        MultiplayerManager.communicationManager.init();
    }

    public static void addMultiplayerGame(MultiplayerGameInfo gameInfo) {
        foundGames.add(gameInfo);
        Game.getEventBus().post(new MultiplayerGameFoundEvent());
    }

    public static List<MultiplayerGameInfo> getFoundGames() {
        return foundGames;
    }

    public static MultiplayerGameInfo getCurrentHostedGame() {
        return currentHostedGame;
    }

    public static MultiplayerRole getCurrentRole() {
        return currentRole;
    }

    public static void discoverGames() {
        MultiplayerManager.foundGames.clear();
        MultiplayerManager.communicationManager.requestPeers();
    }
}
