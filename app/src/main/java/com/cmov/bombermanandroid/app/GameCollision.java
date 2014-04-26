package com.cmov.bombermanandroid.app;

import com.cmov.bombermanandroid.app.model.Grid;
import com.cmov.bombermanandroid.app.model.Model;
import com.cmov.bombermanandroid.app.model.Movable;

public class GameCollision {

    private static Grid grid;

    public static void setGrid(Grid grid) {
        GameCollision.grid = grid;
    }

    public static boolean checkRightCollision(Movable character){
        Model model = grid.getModel(character.getX() + 1, character.getY());
        if(model == null){
            grid.updateGridAfterMovement(character.getX(), character.getY(), character.getX() + 1, character.getY());
            return false;
        }
        else return model.isCollidable();
    }

    public static boolean checkDeadlyRightCollison(Movable character){
        return false;
    }

    public static boolean checkLeftCollision(Movable character){
        Model model = grid.getModel(character.getX() - 1, character.getY());
        if(model == null){
            grid.updateGridAfterMovement(character.getX(), character.getY(), character.getX() + 1, character.getY());
            return false;
        }
        else return model.isCollidable();
    }

    public static boolean checkDeadlyLeftCollision(Movable character){
        return false;
    }

    public static boolean checkDownCollision(Movable character){
        Model model = grid.getModel(character.getX(), character.getY() - 1);
        if(model == null){
            grid.updateGridAfterMovement(character.getX(), character.getY(), character.getX(), character.getY() - 1);
            return false;
        }
        else return model.isCollidable();
    }

    public static boolean checkDeadlyDownCollision(Movable character){
        return false;
    }

    public static boolean checkUpCollision(Movable character){
        Model model = grid.getModel(character.getX(), character.getY() + 1);
        if(model == null){
            grid.updateGridAfterMovement(character.getX(), character.getY(), character.getX(), character.getY() + 1);
            return false;
        }
        else return model.isCollidable();
    }

    public static boolean checkDeadlyUpCollision(Movable character){
        return false;
    }
}
