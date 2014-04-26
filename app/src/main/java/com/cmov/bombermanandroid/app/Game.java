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
    private static GameCollision gameCollision;

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
            grid.updateGridAfterMovement(character.getX(), character.getY(), character.getX() + 1, character.getY());
            return false;
        }
        else if(model.isCollidable()){
            return true;
        }
        else return checkDeadlyRightCollision(character, (Movable) model);
    }

    private static boolean checkDeadlyRightCollision(Movable character, Movable model) {
        if( !character.isEnemy() && model.isEnemy() ){
            // Bomberman suicides himself
            grid.updateGridAfterBombermanSuicides(character.getX(), character.getY());
            return false;
        }
        else if( character.isEnemy() && !model.isEnemy()){
            // Bomberman was killed
            grid.updateGridAfterMovement(character.getX(), character.getY(), character.getX() + 1, character.getY());
            return false;
        }
        else return true;
    }

    public static boolean checkLeftCollision(Movable character){
        Model model = grid.getModel(character.getX() - 1, character.getY());
        if(model == null){
            grid.updateGridAfterMovement(character.getX(), character.getY(), character.getX() - 1, character.getY());
            return false;
        }
        else if(model.isCollidable()){
            return true;
        }
        else return checkDeadlyLeftCollision(character, (Movable) model);
    }

    private static boolean checkDeadlyLeftCollision(Movable character, Movable model) {
        if( !character.isEnemy() && model.isEnemy() ){
            // Bomberman suicides himself
            grid.updateGridAfterBombermanSuicides(character.getX(),character.getY());
            return false;
        }
        else if( character.isEnemy() && !model.isEnemy()){
            // Bomberman was killed
            grid.updateGridAfterMovement(character.getX(), character.getY(), character.getX() - 1, character.getY());
            return false;
        }
        else return true;
    }


    public static boolean checkUpCollision(Movable character){
        Model model = grid.getModel(character.getX(), character.getY() - 1);
        if(model == null){
            grid.updateGridAfterMovement(character.getX(), character.getY(), character.getX(), character.getY() - 1);
            return false;
        }
        else if(model.isCollidable()){
            return true;
        }
        else return checkDeadlyUpCollision(character, (Movable) model);
     }

    private static boolean checkDeadlyUpCollision(Movable character, Movable model) {
        if( !character.isEnemy() && model.isEnemy() ){
            // Bomberman suicide himself
            grid.updateGridAfterBombermanSuicides(character.getX(), character.getY());
            return false;
        }
        else if( character.isEnemy() && !model.isEnemy()){
            // Bomberman was killed
            grid.updateGridAfterMovement(character.getX(), character.getY(), character.getX(), character.getY() - 1);
            return false;
        }
        else return true;
    }

    public static boolean checkDownCollision(Movable character){
        Model model = grid.getModel(character.getX(), character.getY() + 1);
        if(model == null){
            grid.updateGridAfterMovement(character.getX(), character.getY(), character.getX(), character.getY() + 1);
            return false;
        }
        else if(model.isCollidable()){
            return true;
        }
        else return checkDeadlyDownCollision(character, (Movable) model);
    }

    private static boolean checkDeadlyDownCollision(Movable character, Movable model) {
        if( !character.isEnemy() && model.isEnemy() ){
            // Bomberman suicide himself
            grid.updateGridAfterBombermanSuicides(character.getX(), character.getY());
            return false;
        }
        else if( character.isEnemy() && !model.isEnemy()){
            // Bomberman was killed
            grid.updateGridAfterMovement(character.getX(), character.getY(), character.getX(), character.getY() + 1);
            return false;
        }
        else return true;
    }
}