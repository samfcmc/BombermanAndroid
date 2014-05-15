package com.cmov.bombermanandroid.app.multiplayer.messages;

import com.cmov.bombermanandroid.app.Game;
import com.cmov.bombermanandroid.app.model.Bomberman;
import com.cmov.bombermanandroid.app.multiplayer.JoinedMultiplayerGameInfo;
import com.cmov.bombermanandroid.app.multiplayer.MultiplayerManager;
import com.cmov.bombermanandroid.app.multiplayer.PlayerInfo;
import com.cmov.bombermanandroid.app.multiplayer.communication.CommunicationChannel;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class JoinGameAcceptedMessageReceiver implements MessageReceiver {
    @Override
    public void afterReceive(JsonObject json, CommunicationChannel communicationChannel) {
        String name = json.get("name").getAsString();
        int playerNumber = json.get("player").getAsInt();
        int level = json.get("level").getAsInt();
        JsonArray playersJsonArray = json.getAsJsonArray("players");

        List<PlayerInfo> joinedPlayers = new ArrayList<PlayerInfo>();

        for(JsonElement jsonElement : playersJsonArray) {
            JsonObject playerJsonObject = jsonElement.getAsJsonObject();
            int playerNumberToJoin = playerJsonObject.get("player").getAsInt();
            int x = playerJsonObject.get("x").getAsInt();
            int y = playerJsonObject.get("y").getAsInt();
            joinedPlayers.add(new PlayerInfo(playerNumberToJoin, x, y));
        }

        JoinedMultiplayerGameInfo gameInfo = new JoinedMultiplayerGameInfo(name, 0,
                communicationChannel, playerNumber, level, joinedPlayers);
        MultiplayerManager.joinAccepted(gameInfo);

        //Inform master that I finished joined
        communicationChannel.sendMessage(MessageFactory.createJoinAckMessage(playerNumber));
    }
}
