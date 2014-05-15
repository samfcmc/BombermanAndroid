package com.cmov.bombermanandroid.app.multiplayer.roles;

import com.cmov.bombermanandroid.app.Game;
import com.cmov.bombermanandroid.app.multiplayer.JoinedMultiplayerGameInfo;
import com.cmov.bombermanandroid.app.multiplayer.MultiplayerManager;
import com.cmov.bombermanandroid.app.multiplayer.PlayerInfo;
import com.cmov.bombermanandroid.app.multiplayer.messages.MessageFactory;

import java.util.List;

public class SlaveMultiplayerRole extends MultiplayerRole {

    @Override
    public void notifyAboutUpdate() {

    }

    @Override
    public String createAskGameResponseMessage() {
        return MessageFactory.createNoMultiplayerGameCreatedMessage();
    }

    @Override
    public int getLocalPlayer() {
        //TODO: The master must give an id to the player
        return 0;
    }

    @Override
    public void start() {
        JoinedMultiplayerGameInfo joinedGame = MultiplayerManager.getCurrentJoinedGame();
        List<PlayerInfo> playersToJoin = joinedGame.getPlayers();

        for(PlayerInfo playerToJoin : playersToJoin) {
            Game.joinPlayerInPosition(playerToJoin.getPlayerNumber(), playerToJoin.getX(),
                    playerToJoin.getY());
        }

        Game.joinPlayer(joinedGame.getPlayerNumber());
    }

}
