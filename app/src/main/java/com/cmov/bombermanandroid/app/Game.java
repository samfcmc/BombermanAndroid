package com.cmov.bombermanandroid.app;

import android.graphics.Canvas;
import android.graphics.Color;
import com.cmov.bombermanandroid.app.commands.*;
import com.cmov.bombermanandroid.app.model.*;
import com.cmov.bombermanandroid.app.text.PauseText;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class Game {

    private static List<Bomberman> players = new ArrayList<Bomberman>();
    private static List<Enemy> enemies = new ArrayList<Enemy>();
    private static boolean paused = false;
    private static PauseText pauseText = new PauseText(Color.WHITE);
    private static Queue<Command> commands = new LinkedList<Command>();
    private static Grid grid;

    public static void setGrid(Grid grid1) {
        grid = grid1;
    }

    public static void updateGameState(Canvas canvas) {
        processCommands();

        if(!paused) {
            updateMovables(players, canvas);
            updateMovables(enemies, canvas);
            //updatePlayers();
            updateBomb(0);
            generateCommandForEnemies();
        }
    }

    private static void updateBomb(int player) {
        Bomberman bomberman = players.get(player);
        bomberman.update(System.currentTimeMillis());
    }

  /*  private static void updatePlayers() {
        for(Bomberman player : players) {
            if(player.hasBomb()){
                updateBomb(player.playerID);
            }
        }
    }*/

    private static void processCommands() {
        if(!commands.isEmpty()) {
            Command command = commands.remove();
            command.execute();
        }
    }

    public static void addCommand(Command command) {
        commands.add(command);
    }

    private static void updateMovables(List<? extends Movable> characters, Canvas canvas) {
        for (Movable character : characters) {
            if (!(character.getCommand() == null)) {
                CharacterCommand command = character.getCommand();
                command.execute();
            }
            if (character.isMoving()) {
                if (character.shouldStop(character.getScaledBitmap(canvas))) {
                    character.stopAndUpdatePosition(grid);
                }
            }
        }
    }

    public static boolean isPaused() {
        return paused;
    }

    public static void setPaused(boolean paused) {
        Game.paused = paused;
    }

    public static Bomberman getPlayer(int player) {
        return players.get(player);
    }

    public static void dropBomb(int player) {
        getPlayer(player).dropBomb(grid);
    }

    public static void addPlayer(Bomberman bomberman) {
        players.add(bomberman);
    }

    public static void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public static void reset() {
        players.clear();
        enemies.clear();
    }

    public static void draw(Canvas canvas) {
        if(paused) {
            pauseText.draw(canvas);
        }
        else {
            grid.draw(canvas);
        }
    }

    public static boolean checkCollision(Movable character, int x, int y) {
        Model model;
        if (character.isMoving()) {
            model = grid.getModel(character.getXAfterMovement(), character.getYAfterMovement());
        } else {
            model = grid.getModel(x, y);
        }

        if (model == null || model == character) {
            return false;
        } else return model.isCollidable();
    }

    /*
     * Enemies behaviour
     */
    public static void generateCommandForEnemies() {
        for (Movable enemy : enemies) {
            if (!enemy.isMoving()) {
                CharacterCommand randomCommand = generateRandomCommand(enemy);
                enemy.setCommand(randomCommand);
            }
        }
    }

    private static CharacterCommand generateRandomCommand(Movable character) {
        int rand = (int) (Math.random() * 4);
        CharacterCommand command;
        switch (rand) {
            case 0:
                command = new UpCommand(character);
                break;
            case 1:
                command = new DownCommand(character);
                break;
            case 2:
                command = new LeftCommand(character);
                break;
            default:
                command = new RightCommand(character);
                break;
        }

        return command;
    }

}