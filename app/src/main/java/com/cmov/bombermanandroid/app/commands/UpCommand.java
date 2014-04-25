package com.cmov.bombermanandroid.app.commands;

import com.cmov.bombermanandroid.app.Game;
import com.cmov.bombermanandroid.app.model.Movable;

public class UpCommand extends CharacterCommand {

    public UpCommand(Movable character) {
        super(character);
    }

    @Override
    public void execute() {
        if (!Game.checkCollision(getCharacter(), getCharacter().getX(), getCharacter().getY() - 1)) {
            getCharacter().startMovingToUp();
        }
        else {
            getCharacter().stopMoving();
        }
    }
}
