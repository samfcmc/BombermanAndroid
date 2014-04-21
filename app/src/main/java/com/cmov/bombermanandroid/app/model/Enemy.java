package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;

import java.lang.*;

public class Enemy extends Movable {


    public Enemy(Bitmap bitmap, int x, int y, float speed, boolean isDead){
        super(bitmap, x, y, speed, isDead);
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
