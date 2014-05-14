package com.cmov.bombermanandroid.app.modes;

import com.cmov.bombermanandroid.app.Game;
import com.cmov.bombermanandroid.app.commands.DownCommand;
import com.cmov.bombermanandroid.app.commands.LeftCommand;
import com.cmov.bombermanandroid.app.commands.RightCommand;
import com.cmov.bombermanandroid.app.commands.UpCommand;
import com.cmov.bombermanandroid.app.model.Bomb;
import com.cmov.bombermanandroid.app.model.Bomberman;
import com.cmov.bombermanandroid.app.threads.ExplosionThread;

import java.util.Timer;

public class SinglePlayerModeManager extends ModeManager {

    private static final int LOCAL_PLAYER = 1;
    @Override
    public void start() {
        Game.joinPlayer(LOCAL_PLAYER);
        Game.setLocalPlayerNumber(LOCAL_PLAYER);
    }

    @Override
    public void upPressed() {
        Bomberman player = Game.getLocalPlayer();
        Game.trySendCommand(player, new UpCommand(player));
    }

    @Override
    public void downPressed() {
        Bomberman player = Game.getLocalPlayer();
        Game.trySendCommand(player, new DownCommand(player));
    }

    @Override
    public void leftPressed() {
        Bomberman player = Game.getLocalPlayer();
        Game.trySendCommand(player, new LeftCommand(player));
    }

    @Override
    public void rightPressed() {
        Bomberman player = Game.getLocalPlayer();
        Game.trySendCommand(player, new RightCommand(player));
    }

    @Override
    public void bombPressed() {
        Bomberman player = Game.getLocalPlayer();

        if(Game.getCurrentState().isCanDropBomb()) {
            Bomb bomb = player.dropBomb(Game.getGrid(), Game.getBombs(), System.currentTimeMillis());
            if (bomb != null) {
                new Timer().schedule(new ExplosionThread(bomb), bomb.getTimeout());
            }
        }
    }

    @Override
    public void quitPressed() {
        // TODO
    }
}
