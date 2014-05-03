package com.cmov.bombermanandroid.app.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import com.cmov.bombermanandroid.app.R;
import com.cmov.bombermanandroid.app.logic.ExplosionLogic;
import com.cmov.bombermanandroid.app.logic.Point2D;

import java.util.ArrayList;
import java.util.List;

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

    private Cell[][] gameMap;

    /**
     * Instantiates a new Grid.
     *
     * @param context the context
     */
    public Grid(Context context) {
        this.floor = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        this.gameMap = new Cell[HEIGHT][WIDTH];
        // Initialize all grid positions with cells
        for (int i = 0; i < HEIGHT; i++){
            for (int j = 0; j < WIDTH; j++){
                this.gameMap[i][j] = new Cell();
            }
        }

    }

    /**
     * Add bomb.
     *
     * @param bomb the bomb
     */
    public void addBomb(Bomb bomb) {
        this.gameMap[bomb.getY()][bomb.getX()].addModel(bomb);
    }

    public void addExplosion(Explosion explosion) { this.gameMap[explosion.getY()][explosion.getX()].addModel(explosion); }
    /**
     * Add bomberman.
     *
     * @param bomberman the bomberman
     */
    public void addBomberman(Bomberman bomberman) {
        this.gameMap[bomberman.getY()][bomberman.getX()].addModel(bomberman);
    }

    /**
     * Add floor.
     *
     * @param x the x
     * @param y the y
     */
    public void addFloor(int x, int y) { return;}

    /**
     * Add obstacle.
     *
     * @param obstacle the obstacle
     */
    public void addObstacle(Obstacle obstacle) {
        this.gameMap[obstacle.getY()][obstacle.getX()].addModel(obstacle);
    }

    /**
     * Add robot.
     *
     * @param robot the robot
     */
    public void addRobot(Robot robot) {
        this.gameMap[robot.getY()][robot.getX()].addModel(robot);
    }

    /**
     * Add wall.
     *
     * @param wall the wall
     */
    public void addWall(Wall wall) {
        this.gameMap[wall.getY()][wall.getX()].addModel(wall);
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
                Cell cell = this.gameMap[i][j];
                if (cell != null)
                    cell.draw(canvas);
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
    public void updateGrid(int srcX, int srcY, int destX, int destY, Model model) {
        if (!((srcX == destX) && (srcY == destY))) {
            getCell(destY, destX).addModel(model);
            getCell(srcY, srcX).removeModel(model);
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
        if(this.gameMap[y][x].isEmpty()) {
            return null;
        }
        else return this.gameMap[y][x].getModels().get(0);
    }

    public static void calculateTileSize(float canvasWidth, float canvasHeight) {
        TILE_WIDHT = canvasWidth / Grid.WIDTH;
        TILE_HEIGHT = canvasHeight / Grid.WIDTH;
    }

    public boolean isWall(int x, int y) { return  this.getModel(x,y) instanceof Wall; }

    //given a point (x,y) a range and a calculator
    // this method returns all crossed except those which represent a wall
    public List<Point2D> getCrossedPoints(int x, int y, int range, ExplosionLogic calculator) {
        List<Point2D> points = new ArrayList<Point2D>();
        for(Point2D point : calculator.calculatePoints(x,y,range)){
            if(!this.isWall(point.getX(),point.getY())) {
                points.add(point);
            }
        }
        return points;
    }

    public void removeModel(Model model) {
        this.gameMap[model.getY()][model.getX()].removeModel(model);
    }

    public Cell getCell(int line, int column) {
        return this.gameMap[line][column];
    }

    public boolean isBomb(int x, int y) {
        return getModel(x,y) instanceof Bomb;
    }

}