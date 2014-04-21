package com.cmov.bombermanandroid.app;

import com.cmov.bombermanandroid.app.commands.Command;

import java.util.LinkedList;
import java.util.Queue;

public class Game {

    private static Queue<Command> userCommands = new LinkedList<Command>();
    private Queue<Command> generatedCommands = new LinkedList<Command>();

    public void addUserCommand(Command command){
        userCommands.add(command);
    }

    public void addGeneratedCommand(Command command){
        generatedCommands.add(command);
    }

}