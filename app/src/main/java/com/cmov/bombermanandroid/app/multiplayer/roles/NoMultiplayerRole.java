package com.cmov.bombermanandroid.app.multiplayer.roles;

import com.cmov.bombermanandroid.app.commands.CharacterCommand;
import com.cmov.bombermanandroid.app.model.Bomberman;
import com.cmov.bombermanandroid.app.multiplayer.messages.MessageFactory;
import com.google.gson.JsonObject;

/**
 * When the peer didn't host a game neither
 * joined one
 */
public class NoMultiplayerRole extends MultiplayerRole {
    @Override
    public void notifyAboutUpdate() {

    }

    @Override
    public String createAskGameResponseMessage() {
        return MessageFactory.createNoMultiplayerGameCreatedMessage();
    }

    @Override
    public int getLocalPlayer() {
        return -1;
    }

    @Override
    public void start() {

    }

    @Override
    public void update() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateEnemies() {

    }

    @Override
    public void receiveMoveUpdate(JsonObject jsonUpdateMessage, CharacterCommand command) {

    }

    @Override
    public void receiveBombUpdate(Bomberman bomberman) {

    }

    @Override
    public void upPressed() {

    }

    @Override
    public void downPressed() {

    }

    @Override
    public void leftPressed() {

    }

    @Override
    public void rightPressed() {

    }

    @Override
    public void bombPressed() {

    }
}
