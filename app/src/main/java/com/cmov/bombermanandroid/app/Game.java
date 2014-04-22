package com.cmov.bombermanandroid.app;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.cmov.bombermanandroid.app.commands.Command;
import com.cmov.bombermanandroid.app.model.Bomberman;
import com.cmov.bombermanandroid.app.model.Enemy;
import com.cmov.bombermanandroid.app.model.Grid;
import com.cmov.bombermanandroid.app.model.Model;
import com.cmov.bombermanandroid.app.model.Movable;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private enum Directions {LEFT, RIGHT, UP, DOWN}

    private static List<Bomberman> players = new ArrayList<Bomberman>();
    private static List<Enemy> enemies = new ArrayList<Enemy>();
    private static Grid grid;

    public static void setGrid(Grid grid1) {
        grid = grid1;
    }

    public static void processNextCommands() {
        processNextCommandsInCharacters(players);
        processNextCommandsInCharacters(enemies);
    }

    private static void processNextCommandsInCharacters(List<? extends Movable> characters) {
        for (Movable character : characters) {
            if (!character.commandsQueueIsEmpty()) {
                Command command = character.popNextCommand();
                command.execute();
            }
        }
    }

    public static Bomberman getPlayer(int player) {
        return players.get(player);
    }

    public static void moveRight(Movable character) {
        // Need to verify if the right position
        if(!checkCollision(character, Directions.RIGHT))
            grid.move(character.getX(), character.getY(), character.getX() + 1, character.getY());
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

    private static boolean checkCollision(Movable character, Directions direction){

        Model model;

        switch (direction){
            case LEFT :
                model = grid.getModel(character.getX() - 1, character.getY());
                break;
            case RIGHT :
                model = grid.getModel(character.getX() + 1, character.getY());
                break;
            case UP :
                model = grid.getModel(character.getX(), character.getY() + 1);
                break;
            case DOWN :
                model = grid.getModel(character.getX(), character.getY() - 1);
                break;
            default :
                model = null;
                break;
        }

        if(model == null){
            return false;
        }
        else return model.isCollidable();
    }
}