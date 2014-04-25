package com.cmov.bombermanandroid.app;

import android.graphics.Canvas;

import com.cmov.bombermanandroid.app.commands.Command;
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
            if (!character.commandsQueueIsEmpty() && !character.isMoving()) {
                Command command = character.popNextCommand();
                command.execute();
            }
            else if(character.isMoving()) {
                if(character.shouldStop(character.getScaledBitmap(canvas))) {
                    character.stopAndUpdatePosition(grid);
                }
            }
        }
    }

    public static Bomberman getPlayer(int player) {
        return players.get(player);
    }

    public static void moveRight(Movable character) {
        if(!checkRightCollision(character)){
            character.startMovingToRight();
        }
    }

    public static void moveLeft(Movable character) {
        if(!checkLeftCollision(character)){
            character.startMovingToLeft();
        }
    }

    public static void moveUp(Movable character) {
        if(!checkUpCollision(character)){
            character.startMovingToUp();
        }
    }

    public static  void moveDown(Movable character) {
        if(!checkDownCollision(character)){
            character.startMovingToDown();
        }
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

    public static boolean checkRightCollision(Movable character){
        Model model = grid.getModel(character.getX() + 1, character.getY());
        if(model == null){
            return false;
        }
        else return model.isCollidable();
    }

    public static boolean checkLeftCollision(Movable character){
        Model model = grid.getModel(character.getX() - 1, character.getY());
        if(model == null){
            return false;
        }
        else return model.isCollidable();
    }

    public static boolean checkUpCollision(Movable character){
        Model model = grid.getModel(character.getX(), character.getY() - 1);
        if(model == null){
            return false;
        }
        else return model.isCollidable();
    }

    public static boolean checkDownCollision(Movable character){
        Model model = grid.getModel(character.getX(), character.getY() + 1);
        if(model == null){
            return false;
        }
        else return model.isCollidable();
    }
}