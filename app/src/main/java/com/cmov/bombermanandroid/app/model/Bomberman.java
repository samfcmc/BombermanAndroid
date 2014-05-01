package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import com.cmov.bombermanandroid.app.BitmapLib;
import com.cmov.bombermanandroid.app.GameLoader;

import java.util.Queue;

public class Bomberman extends Movable {

    public int playerID;
    public int lives;
    private long lastBomb;

    public Bomberman(Bitmap bitmap, int x, int y, int playerID, int lives, float speed, boolean isDead){
        super(bitmap, x, y, speed, isDead);
        this.playerID = playerID;
        this.lives = lives;
        this.lastBomb = 0;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public long getLastBomb() { return this.lastBomb; }

    public void dropBomb(Grid grid, Queue<Bomb> bombs, long dt){
        long timePassed = dt - lastBomb;
        //get timeout
        GameLoader gameLoader = GameLoader.getInstance();
        long explosionTimeout = (long) 1000 * gameLoader.getSetting(GameLoader.GAME_SETTINGS.ET); //timeout

       if(timePassed > explosionTimeout) {
            Bomb bomb = new
                    Bomb(BitmapLib.getBombBitmap(),
                    this.getX() + 1, //implant the bomb in the next tile
                    // TODO: put the bomb in the same(x,y) than bomberman
                    this.getY(),
                    gameLoader.getSetting(GameLoader.GAME_SETTINGS.ER), //range
                    gameLoader.getSetting(GameLoader.GAME_SETTINGS.ED), //duration
                    explosionTimeout, //timeout
                    grid,   //the current grid
                    dt);
            //add bomb in the grid
            grid.addBomb(bomb);
            //create explosions
            bomb.createExplosions();
            bombs.add(bomb);
            this.lastBomb = dt;
       }
    }

}
