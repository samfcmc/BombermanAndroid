package com.cmov.bombermanandroid.app.commands;

import com.cmov.bombermanandroid.app.Game;
import com.cmov.bombermanandroid.app.model.Movable;

public class LeftCommand extends CharacterCommand {

    public LeftCommand(Movable character) {
        super(character);
    }

    @Override
    public void execute() {
        if (!Game.checkCollision(getCharacter(), getCharacter().getX() - 1, getCharacter().getY())) {
            getCharacter().startMovingToLeft();
        }
    }
}
