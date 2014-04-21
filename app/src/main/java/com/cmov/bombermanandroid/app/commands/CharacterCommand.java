package com.cmov.bombermanandroid.app.commands;

import com.cmov.bombermanandroid.app.model.Movable;

public class CharacterCommand extends Command {

    private Movable character;

    public CharacterCommand(Movable character) {
        this.character = character;
    }

    public Movable getCharacter() {
        return character;
    }

    @Override
    public void execute() {

    }
}
