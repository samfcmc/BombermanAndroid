package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;


public class Obstacle extends Model {

    public boolean isVisible;
    public int hitPoints;

    public Obstacle(Bitmap bitmap, int x, int y, int hitPoints, boolean isVisible){
        super(bitmap, x, y, true);
        this.hitPoints = hitPoints;
        this.isVisible = isVisible;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
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
