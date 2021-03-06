package com.cmov.bombermanandroid.app.multiplayer.roles;

import com.cmov.bombermanandroid.app.commands.CharacterCommand;
import com.cmov.bombermanandroid.app.model.Bomberman;
import com.google.gson.JsonObject;

public abstract class MultiplayerRole {

    public abstract void notifyAboutUpdate();
    public abstract String createAskGameResponseMessage();
    public abstract int getLocalPlayer();
    public abstract void start();
    public abstract void update();
    public abstract void updateEnemies();
    public abstract void receiveMoveUpdate(JsonObject jsonUpdateMessage, CharacterCommand command);
    public abstract void receiveBombUpdate(Bomberman bomberman);

    public abstract void upPressed();

    public abstract void downPressed();

    public abstract void leftPressed();

    public abstract void rightPressed();

    public abstract void bombPressed();
}
