package com.cmov.bombermanandroid.app.commands;

import com.cmov.bombermanandroid.app.Game;
import com.cmov.bombermanandroid.app.model.Movable;

public class DownCommand extends CharacterCommand {

    public DownCommand(Movable character) {
        super(character);
    }

    @Override
    public void execute() {
        if (!Game.checkCollision(getCharacter(), getCharacter().getX(), getCharacter().getY() + 1)) {
            getCharacter().startMovingToDown();
        }
    }
}



