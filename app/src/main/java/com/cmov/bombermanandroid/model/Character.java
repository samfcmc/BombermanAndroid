package com.cmov.bombermanandroid.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

public class Character extends Drawable {

    private Bitmap bitmap;
    private int x;
    private int y;
    private float speed;
    private boolean isDead;


    public Character(Bitmap bitmap, int x, int y, float speed, boolean isDead){
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.isDead = isDead;

    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
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
