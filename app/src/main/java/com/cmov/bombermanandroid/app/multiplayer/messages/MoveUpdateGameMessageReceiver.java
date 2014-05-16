package com.cmov.bombermanandroid.app.multiplayer.messages;

import com.cmov.bombermanandroid.app.Game;
import com.cmov.bombermanandroid.app.commands.CharacterCommand;
import com.cmov.bombermanandroid.app.commands.Command;
import com.cmov.bombermanandroid.app.commands.DownCommand;
import com.cmov.bombermanandroid.app.commands.LeftCommand;
import com.cmov.bombermanandroid.app.commands.RightCommand;
import com.cmov.bombermanandroid.app.commands.UpCommand;
import com.cmov.bombermanandroid.app.model.Movable;
import com.cmov.bombermanandroid.app.multiplayer.MultiplayerManager;
import com.cmov.bombermanandroid.app.multiplayer.communication.CommunicationChannel;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class MoveUpdateGameMessageReceiver implements MessageReceiver {

    private static Map<String, MovableGetter> movableGetters =
            new HashMap<String, MovableGetter>();

    private static  Map<String, CommandGetter> commandGetters =
            new HashMap<String, CommandGetter>();


    private static void bootstrap(){
        movableGetters.put("player", new MovableGetter() {

            @Override
            public Movable getMovable(int movableIndex) {
                return Game.getPlayer(movableIndex);
            }
        });

        movableGetters.put("enemy", new MovableGetter() {

            @Override
            public Movable getMovable(int movableIndex) {
                return Game.getEnemy(movableIndex);
            }
        });

        commandGetters.put("up", new CommandGetter() {
            @Override
            public CharacterCommand getCommand(Movable movable) {
                return new UpCommand(movable);
            }
        });

        commandGetters.put("down", new CommandGetter() {
            @Override
            public CharacterCommand getCommand(Movable movable) {
                return new DownCommand(movable);
            }
        });

        commandGetters.put("left", new CommandGetter() {
            @Override
            public CharacterCommand getCommand(Movable movable) {
                return new LeftCommand(movable);
            }
        });

        commandGetters.put("right", new CommandGetter() {
            @Override
            public CharacterCommand getCommand(Movable movable) {
                return new RightCommand(movable);
            }
        });
    }


    static {
        bootstrap();
    }

    @Override
    public void afterReceive(JsonObject json, CommunicationChannel communicationChannel) {
        String movableType = json.get("movable").getAsString();
        int movableIndex = json.get("number").getAsInt();
        Movable movable = movableGetters.get(movableType).getMovable(movableIndex);
        String direction = json.get("direction").getAsString();
        CharacterCommand command = commandGetters.get(direction).getCommand(movable);
        MultiplayerManager.getCurrentRole().receiveMoveUpdate(json, command);
    }

    private static abstract class MovableGetter {

        public abstract Movable getMovable(int movableIndex);
    }

    private static abstract class CommandGetter {

        public abstract CharacterCommand getCommand(Movable movable);
    }
}
