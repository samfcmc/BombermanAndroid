package com.cmov.bombermanandroid.app.model;

import android.content.Context;
import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.cmov.bombermanandroid.app.Game;
import com.cmov.bombermanandroid.app.R;

public class Grid {

    public static final int HEIGHT = 10;
    public static final int WIDTH = 10;

    Bitmap floor;

    private Model[][] gameMap;

    public Grid(Context context){
       this.floor = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
       this.gameMap = new Model[HEIGHT][WIDTH];
    }

    public void addBomb(Bomb bomb){
        this.gameMap[bomb.getX()][bomb.getY()] = bomb;
    }

    public void addBomberman(Bomberman bomberman){
        this.gameMap[bomberman.getX()][bomberman.getY()] = bomberman;
    }

    public void addFloor(int x, int y){
      this.gameMap[x][y] = null;
    }

    public void addObstacle(Obstacle obstacle){
       this.gameMap[obstacle.getX()][obstacle.getY()] = obstacle;
    }

    public void addRobot(Robot robot){
        this.gameMap[robot.getX()][robot.getY()] = robot;
    }

    public void addWall(Wall wall){
      this.gameMap[wall.getX()][wall.getY()] = wall;
    }

    public void draw(Canvas canvas){

        Bitmap background = Bitmap.createScaledBitmap(this.floor, canvas.getWidth(), canvas.getHeight(), false);
        canvas.drawBitmap(background, 0, 0, null);

        for(int i = 0; i < HEIGHT; i++){
            for(int j = 0; j < WIDTH; j++){
                Model model = this.gameMap[i][j];
                if(model != null)
                    model.draw(canvas);
            }
        }
    }

    public void move(int srcX, int srcY, int destX, int destY) {
        this.gameMap[destX][destY] = this.gameMap[srcX][srcY];
        this.gameMap[destX][destY].move(destX, destY);
        this.gameMap[srcX][srcY] = null;
    }

    public Model getModel(int x, int y){
        return this.gameMap[x][y];
    }
}