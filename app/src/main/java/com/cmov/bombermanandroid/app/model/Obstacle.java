package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;


public class Obstacle extends StaticModel {

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
    public void touchedByExplosion(Explosion explosion) {
        setVisible(false,false);
    }
}
