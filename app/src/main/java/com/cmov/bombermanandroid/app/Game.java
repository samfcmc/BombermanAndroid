package com.cmov.bombermanandroid.app;

import android.graphics.Canvas;

import com.cmov.bombermanandroid.app.commands.CharacterCommand;
import com.cmov.bombermanandroid.app.commands.Command;
import com.cmov.bombermanandroid.app.commands.DownCommand;
import com.cmov.bombermanandroid.app.commands.LeftCommand;
import com.cmov.bombermanandroid.app.commands.RightCommand;
import com.cmov.bombermanandroid.app.commands.UpCommand;
import com.cmov.bombermanandroid.app.model.Bomberman;
import com.cmov.bombermanandroid.app.model.Enemy;
import com.cmov.bombermanandroid.app.model.Grid;
import com.cmov.bombermanandroid.app.model.Model;
import com.cmov.bombermanandroid.app.model.Movable;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private static List<Bomberman> players = new ArrayList<Bomberman>();
    private static List<Enemy> enemies = new ArrayList<Enemy>();
    private static Grid grid;

    public static void setGrid(Grid grid1) {
        grid = grid1;
    }

    public static void update(Canvas canvas) {
        updateMovables(players, canvas);
        updateMovables(enemies, canvas);
    }

    private static void updateMovables(List<? extends Movable> characters, Canvas canvas) {
        for (Movable character : characters) {
            if (!(character.getCommand() == null) && !character.isMoving()) {
                Command command = character.getCommand();
                command.execute();
            } else if (character.isMoving()) {
                if (character.shouldStop(character.getScaledBitmap(canvas))) {
                    character.stopAndUpdatePosition(grid);
                }
            }
        }
    }

    public static Bomberman getPlayer(int player) {
        return players.get(player);
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
        grid.draw(canvas);
    }

    public static boolean checkCollision(Movable character, int x, int y) {
        Model model = grid.getModel(x, y);
        if (model == null) {
            return false;
        } else return model.isCollidable();
    }

    /*
     * Enemies behaviour
     */
    public static void generateCommandForEnemies() {
        for (Movable enemy : enemies) {
            CharacterCommand randomCommand = generateRandomCommand(enemy);
            enemy.setCommand(randomCommand);
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