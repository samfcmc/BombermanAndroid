package com.cmov.bombermanandroid.app.multiplayer.roles;

import com.cmov.bombermanandroid.app.Game;
import com.cmov.bombermanandroid.app.commands.CharacterCommand;
import com.cmov.bombermanandroid.app.commands.Command;
import com.cmov.bombermanandroid.app.commands.DownCommand;
import com.cmov.bombermanandroid.app.commands.LeftCommand;
import com.cmov.bombermanandroid.app.commands.RightCommand;
import com.cmov.bombermanandroid.app.commands.UpCommand;
import com.cmov.bombermanandroid.app.model.Bomberman;
import com.cmov.bombermanandroid.app.model.Enemy;
import com.cmov.bombermanandroid.app.model.Grid;
import com.cmov.bombermanandroid.app.model.Movable;
import com.cmov.bombermanandroid.app.modes.GameMode;
import com.cmov.bombermanandroid.app.multiplayer.MultiplayerManager;
import com.cmov.bombermanandroid.app.multiplayer.messages.MessageFactory;
import com.google.gson.JsonObject;

import java.util.List;

public class MasterMultiplayerRole extends MultiplayerRole {

    private static final int LOCAL_PLAYER = 1;



    @Override
    public void notifyAboutUpdate() {

    }

    @Override
    public String createAskGameResponseMessage() {
        return MessageFactory.createMultiplayerGameCreatedMessage(
                MultiplayerManager.getCurrentHostedGame());
    }

    @Override
    public int getLocalPlayer() {
        return LOCAL_PLAYER;
    }

    @Override
    public void start() {
        Game.joinPlayer(LOCAL_PLAYER);
        Game.setLocalPlayerNumber(LOCAL_PLAYER);
    }

    @Override
    public void update() {

    }

    @Override
    public void updateEnemies() {
        Game.generateCommandForEnemies();
        // TODO: Send only the updated positions
    }

    @Override
    public void receiveMoveUpdate(JsonObject jsonUpdateMessage, CharacterCommand command) {
        JsonObject jsonUpdateNotify = new JsonObject();
        jsonUpdateNotify.addProperty("type", "update");
        jsonUpdateNotify.add("update", jsonUpdateMessage);
        MultiplayerManager.sendToSlaves(jsonUpdateNotify);
        command.getCharacter().setCommand(command);
    }

    @Override
    public void receiveBombUpdate(Bomberman bomberman) {
        JsonObject jsonUpdateNotify = new JsonObject();
        jsonUpdateNotify.addProperty("type", "update");
        jsonUpdateNotify.addProperty("update", "bomb");
        jsonUpdateNotify.addProperty("player", bomberman.getPlayerNumber());
        MultiplayerManager.sendToSlaves(jsonUpdateNotify);
    }

    @Override
    public void upPressed() {
        String message = MessageFactory.createPlayerMoveUpMessage(Game.getLocalPlayer().getPlayerNumber());
        MultiplayerManager.sendToSlaves(message);
        CharacterCommand command = new UpCommand(Game.getLocalPlayer());
        Game.sendCommandToLocalPlayer(command);
    }

    @Override
    public void downPressed() {
        String message = MessageFactory.createPlayerMoveDownMessage(Game.getLocalPlayer()
                .getPlayerNumber());
        MultiplayerManager.sendToSlaves(message);
        CharacterCommand command = new DownCommand(Game.getLocalPlayer());
        Game.sendCommandToLocalPlayer(command);
    }

    @Override
    public void leftPressed() {
        String message = MessageFactory.createPlayerMoveLeftMessage(Game.getLocalPlayer().getPlayerNumber());
        MultiplayerManager.sendToSlaves(message);
        CharacterCommand command = new LeftCommand(Game.getLocalPlayer());
        Game.sendCommandToLocalPlayer(command);
    }

    @Override
    public void rightPressed() {
        String message = MessageFactory.createPlayerMoveRightMessage(Game.getLocalPlayer().getPlayerNumber());
        MultiplayerManager.sendToSlaves(message);
        CharacterCommand command = new RightCommand(Game.getLocalPlayer());
        Game.sendCommandToLocalPlayer(command);
    }

    @Override
    public void bombPressed() {
    }
}
