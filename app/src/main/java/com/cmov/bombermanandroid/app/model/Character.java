package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

public class Character extends Model {

    private float speed;
    private boolean isDead;

    public Character(Bitmap bitmap, int x, int y, float speed, boolean isDead){
        super(bitmap, x, y);
        this.speed = speed;
        this.isDead = isDead;

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
