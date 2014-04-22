package com.cmov.bombermanandroid.app;

import android.graphics.Canvas;

import com.cmov.bombermanandroid.app.commands.Command;
import com.cmov.bombermanandroid.app.model.Bomberman;
import com.cmov.bombermanandroid.app.model.Enemy;
import com.cmov.bombermanandroid.app.model.Grid;
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

    public static void processNextCommands() {
        processMovables(players);
        processMovables(enemies);
    }

    private static void processMovables(List<? extends Movable> characters) {
        for (Movable character : characters) {
            if (!character.commandsQueueIsEmpty() && !character.isMoving()) {
                Command command = character.popNextCommand();
                command.execute();
            }
        }
    }

    public static Bomberman getPlayer(int player) {
        return players.get(player);
    }

    public static void moveRight(Movable character) {
        character.startMovingToRight();
    }

    public static void moveLeft(Movable character) {
        character.startMovingToLeft();
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
}