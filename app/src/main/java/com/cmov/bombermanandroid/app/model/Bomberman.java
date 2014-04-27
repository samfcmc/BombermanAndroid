package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import com.cmov.bombermanandroid.app.BitmapLib;
import com.cmov.bombermanandroid.app.GameLoader;

public class Bomberman extends Movable {

    public int playerID;
    public int lives;
    private Bomb bomb;

    public Bomberman(Bitmap bitmap, int x, int y, int playerID, int lives, float speed, boolean isDead){
        super(bitmap, x, y, speed, isDead);
        this.playerID = playerID;
        this.lives = lives;
        this.bomb = null;
    }

    public void setBomb(Bomb bomb) {
        this.bomb = bomb;
    }

    public boolean hasBomb() {
        return bomb != null;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void dropBomb(Grid grid){
        if(!hasBomb()) {
            //to get game static configuration
            GameLoader gameLoader = GameLoader.getInstance();
            this.bomb = new
                    Bomb(BitmapLib.getBombBitmap(),
                    this.getX() + 1, //implant the bomb in the next tile
                                     // TODO: put the bomb in the same(x,y) than bomberman
                    this.getY(),
                    gameLoader.getSetting(GameLoader.GAME_SETTINGS.ER), //range
                    gameLoader.getSetting(GameLoader.GAME_SETTINGS.ED), //duration
                    gameLoader.getSetting(GameLoader.GAME_SETTINGS.ET), //timeout
                    grid,   //the current grid
                    this);  //The bomberman itself to be able to notify that bomb was implanted
            grid.addBomb(bomb);
            //activate the bomb timer
            bomb.setUpTimer();
        }
    }

    public void update(long l) {
        if(hasBomb()) {
            bomb.updade(l);
        }
    }
}
