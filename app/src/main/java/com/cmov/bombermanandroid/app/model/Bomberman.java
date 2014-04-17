package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;

public class Bomberman extends Character {

    private int lives;

    public Bomberman(Bitmap bitmap, int x, int y, int lives, float speed, boolean isDead){
        super(bitmap, x, y, speed, isDead);
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

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public void setAlpha(int i) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
