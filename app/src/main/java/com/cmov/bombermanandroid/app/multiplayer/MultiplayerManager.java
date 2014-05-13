package com.cmov.bombermanandroid.app.multiplayer;

import com.cmov.bombermanandroid.app.Game;
import com.cmov.bombermanandroid.app.events.MultiplayerGameFoundEvent;
import com.cmov.bombermanandroid.app.multiplayer.communication.CommunicationManager;
import com.cmov.bombermanandroid.app.multiplayer.roles.MultiplayerRole;
import com.cmov.bombermanandroid.app.multiplayer.roles.NoMultiplayerRole;
import java.util.ArrayList;
import java.util.List;

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
