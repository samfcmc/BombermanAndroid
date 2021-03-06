package com.cmov.bombermanandroid.app.multiplayer.messages;

import com.cmov.bombermanandroid.app.model.Bomberman;
import com.cmov.bombermanandroid.app.model.Movable;
import com.cmov.bombermanandroid.app.multiplayer.MultiplayerGameInfo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Collection;

/**
 *
 */
public class MessageFactory {

    public static String createAskForGameMessage() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "game?");
        return json.toString();
    }

    public static String createMultiplayerGameCreatedMessage(MultiplayerGameInfo gameInfo) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "game");
        json.addProperty("name", gameInfo.getName());
        json.addProperty("maxPlayers", gameInfo.getNumberOfPlayers());
        return json.toString();
    }

    public static String createNoMultiplayerGameCreatedMessage() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "noGame");
        return json.toString();
    }

    public static String createJoinGameMessage(Collection<Bomberman> joinedPlayers, String name, Bomberman bomberman) {
        JsonObject json = new JsonObject();
        int playerNumber = bomberman.getPlayerNumber();
        JsonArray playersJsonArray = new JsonArray();
        json.addProperty("type", "join");
        json.addProperty("player", playerNumber);
        json.addProperty("name", name);

        for (Bomberman player : joinedPlayers) {
            JsonObject jsonPlayer = new JsonObject();
            jsonPlayer.addProperty("player", player.getPlayerNumber());
            jsonPlayer.addProperty("x", player.getX());
            jsonPlayer.addProperty("y", player.getY());
            playersJsonArray.add(jsonPlayer);
        }
        json.add("players", playersJsonArray);

        // TODO : update with the correct level
        json.addProperty("level", 0);
        return json.toString();
    }

    public static String createAskForJoinGameMessage() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "join?");
        return json.toString();
    }

    public static String createUpdateGameMessage() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "update");
        // TODO : update the message with the game updates
        return json.toString();
    }

    public static String createPlayerMoveUpMessage(int playerNumber) {
        return createPlayerMoveMessage(playerNumber, "up");
    }

    public static String createPlayerMoveDownMessage(int playerNumber) {
        return createPlayerMoveMessage(playerNumber, "down");
    }

    public static String createPlayerMoveLeftMessage(int playerNumber) {
        return createPlayerMoveMessage(playerNumber, "left");
    }

    public static String createPlayerMoveRightMessage(int playerNumber) {
        return createPlayerMoveMessage(playerNumber, "right");
    }
    public static String createEnemyMoveUpMessage(int playerNumber) {
        return createEnemyMoveMessage(playerNumber, "up");
    }

    public static String createEnemyMoveDownMessage(int playerNumber) {
        return createEnemyMoveMessage(playerNumber, "down");
    }

    public static String createEnemyMoveLeftMessage(int playerNumber) {
        return createEnemyMoveMessage(playerNumber, "left");
    }

    public static String createEnemyMoveRightMessage(int playerNumber) {
        return createEnemyMoveMessage(playerNumber, "right");
    }

    private static String createPlayerMoveMessage(int playerNumber, String direction) {
        return createUpdateJson(createMoveUpdateJson("player", playerNumber, direction));
    }

    private static String createEnemyMoveMessage(int enemyIndex, String direction) {
        return createUpdateJson(createMoveUpdateJson("enemy", enemyIndex, direction));
    }

    private static String createUpdateJson(JsonObject updateJson) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "update");
        json.add("update", updateJson);

        return json.toString();
    }

    private static JsonObject createMoveUpdateJson(String movable, int number, String direction) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "move");
        json.addProperty("movable", movable);
        json.addProperty("number", number);
        json.addProperty("direction", direction);

        return json;
    }

    public static String createJoinRefuseGameMessage() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "joinRefused");
        return json.toString();
    }

    public static String createJoinAckMessage(int playerNumber) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "joinAck");
        json.addProperty("player", playerNumber);
        return json.toString();
    }

}
