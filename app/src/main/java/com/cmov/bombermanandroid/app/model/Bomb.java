package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;

public class Bomb extends StaticModel  {

    private int spread;
    private int damage;
    private int depth;
    private float timeRemaining;

    public Bomb(Bitmap bitmap, int x, int y, int spread, int damage, int depth, float timeRemaining){
        super(bitmap, x, y, false);
        this.spread = spread;
        this.damage = damage;
        this.depth = depth;
        this.timeRemaining = timeRemaining;
    }

    public int getSpread() {
        return spread;
    }

    public void setSpread(int spread) {
        this.spread = spread;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

}
