package com.cmov.bombermanandroid.app.commands;

import android.text.method.MovementMethod;

import com.cmov.bombermanandroid.app.Game;
import com.cmov.bombermanandroid.app.model.Movable;

public class RightCommand extends CharacterCommand {

    public RightCommand(Movable character) {
        super(character);
    }

    @Override
    public void execute() {
        if (!Game.checkCollision(getCharacter(), getCharacter().getX() + 1, getCharacter().getY())) {
            getCharacter().startMovingToRight();
        }
        else {
            getCharacter().stopMoving();
        }
    }
}
