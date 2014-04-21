package com.cmov.bombermanandroid.app.commands;

public class CharacterCommand extends Command {

    private Character character;

    public CharacterCommand(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }

    @Override
    public void execute() {

    }
}
