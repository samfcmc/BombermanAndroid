package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;

public class Bomberman extends Movable {

    public int playerID;
    public int lives;

    public Bomberman(Bitmap bitmap, int x, int y, int playerID, int lives, float speed, boolean isDead){
        super(bitmap, x, y, speed, isDead);
        this.playerID = playerID;
        this.lives = lives;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void dropBomb(){

    }

}
