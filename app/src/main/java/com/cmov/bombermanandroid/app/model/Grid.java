package com.cmov.bombermanandroid.app.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.cmov.bombermanandroid.app.R;

/**
 * The type Grid.
 */
public class Grid {

    /**
     * The grid's HEIGHT (in number of cells).
     */
    public static final int HEIGHT = 19;
    /**
     * The grid's WIDTH (in number of cells).
     */
    public static final int WIDTH = 19;

    public static float TILE_WIDHT;

    public static float TILE_HEIGHT;

    /**
     * The Floor.
     */
    Bitmap floor;

    private Model[][] gameMap;

    /**
     * Instantiates a new Grid.
     *
     * @param context the context
     */
    public Grid(Context context) {
        this.floor = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        this.gameMap = new Model[HEIGHT][WIDTH];
    }

    /**
     * Add bomb.
     *
     * @param bomb the bomb
     */
    public void addBomb(Bomb bomb) {
        this.gameMap[bomb.getX()][bomb.getY()] = bomb;
    }

    public void addExplosion(Explosion explosion) { this.gameMap[explosion.getX()][explosion.getY()] = explosion; }
    /**
     * Add bomberman.
     *
     * @param bomberman the bomberman
     */
    public void addBomberman(Bomberman bomberman) {
        this.gameMap[bomberman.getX()][bomberman.getY()] = bomberman;
    }

    /**
     * Add floor.
     *
     * @param x the x
     * @param y the y
     */
    public void addFloor(int x, int y) {
        this.gameMap[x][y] = null;
    }

    /**
     * Add obstacle.
     *
     * @param obstacle the obstacle
     */
    public void addObstacle(Obstacle obstacle) {
        this.gameMap[obstacle.getX()][obstacle.getY()] = obstacle;
    }

    /**
     * Add robot.
     *
     * @param robot the robot
     */
    public void addRobot(Robot robot) {
        this.gameMap[robot.getX()][robot.getY()] = robot;
    }

    /**
     * Add wall.
     *
     * @param wall the wall
     */
    public void addWall(Wall wall) {
        this.gameMap[wall.getX()][wall.getY()] = wall;
    }

    /**
     * Draw void.
     *
     * @param canvas the canvas
     */
    public void draw(Canvas canvas) {

        Bitmap background = Bitmap.createScaledBitmap(this.floor, canvas.getWidth(), canvas.getHeight(), false);
        canvas.drawBitmap(background, 0, 0, null);

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Model model = this.gameMap[i][j];
                if (model != null)
                    model.draw(canvas);
            }
        }
    }


    /**
     * Update grid.
     * <p/>
     * If scrX == destX and scrY == destY nothing happens
     *
     * @param srcX  the source x
     * @param srcY  the source y
     * @param destX the destination x
     * @param destY the destination y
     */
    public void updateGrid(int srcX, int srcY, int destX, int destY) {
        if (!((srcX == destX) && (srcY == destY))) {
            this.gameMap[destX][destY] = this.gameMap[srcX][srcY];
            this.gameMap[srcX][srcY] = null;
        }

    }

    /**
     * Get a model in a given position.
     *
     * @param x the x
     * @param y the y
     * @return the model
     */
    public Model getModel(int x, int y) {
        return this.gameMap[x][y];
    }

    //this method calculates the width of the tile given a canvas size
    public static float getTileWidth(float canvasWidth) {
        TILE_WIDHT = canvasWidth / Grid.WIDTH;
        return canvasWidth / Grid.WIDTH;
    }
    //this method calculates the height of the tile given a canvas size
    public static float getTileHeight(float canvasHeight) {
        TILE_HEIGHT = canvasHeight / Grid.WIDTH;
        return canvasHeight / Grid.WIDTH;
    }

    public static void calculateTileSize(float canvasWidth, float canvasHeight) {
        TILE_WIDHT = canvasWidth / Grid.WIDTH;
        TILE_HEIGHT = canvasHeight / Grid.WIDTH;
    }
}