package com.cmov.bombermanandroid.app.multiplayer;

import com.cmov.bombermanandroid.app.Game;
import com.cmov.bombermanandroid.app.events.JoinedMultiplayerGameEvent;
import com.cmov.bombermanandroid.app.events.MultiplayerGameFoundEvent;
import com.cmov.bombermanandroid.app.multiplayer.communication.CommunicationManager;
import com.cmov.bombermanandroid.app.multiplayer.messages.MessageFactory;
import com.cmov.bombermanandroid.app.multiplayer.roles.MasterMultiplayerRole;
import com.cmov.bombermanandroid.app.multiplayer.roles.MultiplayerRole;
import com.cmov.bombermanandroid.app.multiplayer.roles.NoMultiplayerRole;
import com.cmov.bombermanandroid.app.multiplayer.roles.SlaveMultiplayerRole;

import java.util.ArrayList;
import java.util.List;

/**
 * MultiplayerManager: This class handles all the multiplayer behavior and
 * act as a wrapper around the WifiDirect API or any simulator of it
 */
public class MultiplayerManager {
    private static final int PORT = 10001;
    private static List<FoundMultiplayerGameInfo> foundGames;
    private static MultiplayerGameInfo currentHostedGame;
    private static MultiplayerRole currentRole;
    private static CommunicationManager communicationManager;
    private static JoinedMultiplayerGameInfo currentJoinedGame;
    private static int localPlayer;

    public static void init(CommunicationManager communicationManager) {
        MultiplayerManager.foundGames = new ArrayList<FoundMultiplayerGameInfo>();
        MultiplayerManager.currentRole = new NoMultiplayerRole();
        MultiplayerManager.communicationManager = communicationManager;
        MultiplayerManager.communicationManager.init();
    }

    public static void createGame(String gameName, int maxPlayers) {
        currentHostedGame = new MultiplayerGameInfo(gameName, maxPlayers);
        currentRole = new MasterMultiplayerRole();
    }

    public static void askToJoinGame(FoundMultiplayerGameInfo gameInfo) {
        communicationManager.sendMessage(gameInfo.getCommunicationChannel(),
                MessageFactory.createAskForJoinGameMessage());
    }

    public static void joinAccepted(JoinedMultiplayerGameInfo gameInfo) {
        currentJoinedGame = gameInfo;
        currentRole = new SlaveMultiplayerRole();
        cleanFoundGames();
        Game.getEventBus().post(new JoinedMultiplayerGameEvent(gameInfo));
    }

    public static void addMultiplayerGame(FoundMultiplayerGameInfo gameInfo) {
        foundGames.add(gameInfo);
        Game.getEventBus().post(new MultiplayerGameFoundEvent());
    }

    public static List<FoundMultiplayerGameInfo> getFoundGames() {
        return foundGames;
    }

    public static MultiplayerGameInfo getCurrentHostedGame() {
        return currentHostedGame;
    }

    public static MultiplayerRole getCurrentRole() {
        return currentRole;
    }

    public static JoinedMultiplayerGameInfo getCurrentJoinedGame() {
        return currentJoinedGame;
    }

    public static void discoverGames() {
        cleanFoundGames();
        MultiplayerManager.communicationManager.requestPeers();
    }

    public static void cleanFoundGames() {
        for (FoundMultiplayerGameInfo gameInfo : foundGames) {
            gameInfo.getCommunicationChannel().close();
        }
        MultiplayerManager.foundGames.clear();
    }

    public static void destroy() {
        cleanFoundGames();
        communicationManager.destroy();
    }
}
