package com.cmov.bombermanandroid.app.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.cmov.bombermanandroid.app.R;

public class Grid {

    public final int HEIGHT = 7;
    public final int WIDTH = 7;

    private Bitmap bomb;
    private Bitmap bomberman;
    private Bitmap floor;
    private Bitmap obstacle;
    private Bitmap robot;
    private Bitmap wall;

    private Model[][] gameMap;

    public Grid(Context context){
        this.bomb = BitmapFactory.decodeResource(context.getResources(), R.drawable.bomb);
        this.bomberman = BitmapFactory.decodeResource(context.getResources(), R.drawable.bomberman);
        this.floor = BitmapFactory.decodeResource(context.getResources(), R.drawable.grass);
        this.obstacle = BitmapFactory.decodeResource(context.getResources(), R.drawable.obstacle);
        this.robot = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);
        this.wall = BitmapFactory.decodeResource(context.getResources(), R.drawable.wall);

        this.gameMap = new Model[HEIGHT][WIDTH];
    }

    public void addBomb(int x, int y, int spread, int damage, int depth){
        Bomb bomb = new Bomb(this.bomb, x, y, spread, damage, depth);
        this.gameMap[x][y] = bomb;
    }

    public void addBomberman(int x, int y, int playerID, int lives, float speed, boolean isDead){
        Bomberman bomberman = new Bomberman(this.bomberman, x, y, playerID, lives, speed, isDead);
        this.gameMap[x][y] = bomberman;
    }

    public void addFloor(int x, int y){
        Floor floor = new Floor(this.floor, x, y);
        this.gameMap[x][y] = floor;
    }

    public void addObstacle(int x, int y, int hitPoints, boolean isVisible){
        Obstacle obstacle = new Obstacle(this.obstacle, x, y, hitPoints, isVisible);
        this.gameMap[x][y] = obstacle;
    }

    public void addRobot(int x, int y, float speed, boolean isDead){
        Robot robot = new Robot(this.robot, x, y, speed, isDead);
        this.gameMap[x][y] = robot;
    }

    public void addWall(int x, int y){
        Wall wall = new Wall(this.wall, x, y);
        this.gameMap[x][y] = wall;
    }

    public void draw(Canvas canvas){
        for(int i = 0; i < HEIGHT; i++){
            for(int j = 0; j < WIDTH; j++){
                Model model = this.gameMap[i][j];
                model.draw(canvas);
            }
        }
    }
}