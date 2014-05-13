package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;

import com.cmov.bombermanandroid.app.BitmapLib;
import com.cmov.bombermanandroid.app.GameLoader;

import java.util.Queue;

public class Bomberman extends Movable {

    private int playerID;
    private int lives;
    private long lastBomb;
    private int pointsPerRobotKilled;
    private int score;

    public Bomberman(Bitmap bitmap, int x, int y, int playerID, int lives, float speed,
                     boolean isEnemy, int pointsPerRobotKilled){
        super(bitmap, x, y, speed, false, isEnemy);
        this.playerID = playerID;
        this.lives = lives;
        this.lastBomb = 0;
        this.pointsPerRobotKilled = pointsPerRobotKilled;
        this.score = 0;
    }

    public int getPointsPerRobotKilled() {
        return pointsPerRobotKilled;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public long getLastBomb() {
        return this.lastBomb;
    }

    public Bomb dropBomb(Grid grid, Queue<Bomb> bombs, long dt) {
        Bomb bomb = null;
        long timePassed = dt - lastBomb;
        //get timeout
        GameLoader gameLoader = GameLoader.getInstance();
        long explosionTimeout = (long) 1000 * gameLoader.getSetting(GameLoader.GAME_SETTINGS.ET); //timeout

        if (timePassed > explosionTimeout) {
            bomb = new
                    Bomb(BitmapLib.getBombBitmap(),
                    this.getX(), //implant the bomb
                    this.getY(),
                    gameLoader.getSetting(GameLoader.GAME_SETTINGS.ER), //range
                    gameLoader.getSetting(GameLoader.GAME_SETTINGS.ED), //duration
                    explosionTimeout, //timeout
                    grid,   //the current grid
                    dt,
                    this);
            //add bomb in the grid
            grid.addBomb(bomb);
            bombs.add(bomb);
            this.lastBomb = dt;
        }
        return bomb;
    }

    @Override
    public void afterExplosion(Explosion explosion) {

    }

    /**
     * Detects a collision between two movable objects
     *
     * @param movable
     */
    @Override
    public void touchedByMovable(Movable movable) {
        if(movable.isEnemy()){
            this.die();
        }
    }


    protected void killedEnemy() {
        this.score += this.pointsPerRobotKilled;
    }

    public int getScore() {
        return score;
    }

    public void setPointsPerRobotKilled(int pointsPerRobotKilled) {
        this.pointsPerRobotKilled = pointsPerRobotKilled;
    }
}
