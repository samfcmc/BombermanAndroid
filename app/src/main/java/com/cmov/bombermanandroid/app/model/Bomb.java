package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import com.cmov.bombermanandroid.app.logic.ExplosionCalculator;
import com.cmov.bombermanandroid.app.logic.NormalExplosion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bomb extends StaticModel {

    public static final String TAG = Bomb.class.getSimpleName();

    List<Explosion> explosions;
    private long timeout;
    private Grid grid;
    private long timeDropped;
    private int range;
    private boolean triggered;
    private boolean explosionOccurred;
    private long realDuration;
    private Bomberman bomberman;

    public Bomb(Bitmap bitmap, int x, int y, int range, int duration, long timeout, Grid grid, long timeDropped, Bomberman bomberman) {
        super(bitmap, x, y, false);
        this.range = range;
        this.timeout = timeout;
        this.grid = grid;
        this.timeDropped = timeDropped;
        this.triggered = true;
        this.explosionOccurred = false; //the bomb has not exploded yet
        this.realDuration = timeout + (long) duration * 1000;

        this.explosions = new ArrayList<Explosion>();
        this.bomberman = bomberman;
    }

    void createCrossedExplosion(ExplosionCalculator calculator) {
        calculator.calculateExplosion();
    }

    public boolean isExplosionOccurred() {
        return explosionOccurred;
    }

    public void setExplosionOccurred(boolean explosionOccurred) {
        this.explosionOccurred = explosionOccurred;
    }

    public void createExplosions() {
        createCrossedExplosion(new NormalExplosion(this));
    }

    public void testExplosionEffect(Explosion explosion) {
        if (this.grid.isBomb(explosion.getX(), explosion.getY())) {
            Bomb bomb = (Bomb) grid.getModel(explosion.getX(), explosion.getY());
            bomb.explode();
        } else {
            this.grid.removeModel(explosion);
        }
    }

    private void explode() {
        this.timeout = 0;
    }

    @Override
    public void draw(Canvas canvas) {
        if(!isExplosionOccurred()) {
            super.draw(canvas);
        }
    }

    public void updateExplosion(long dt) {
        long passedTime = dt - this.timeDropped;
        //if the bomb timeout is running out then triggers the bomb!!
        if (passedTime >= this.timeout) {
            Iterator<Explosion> iterator = explosions.iterator();
            while (iterator.hasNext()) {
                Explosion explosion = iterator.next();
                if (explosion.isAlive()) {
                    explosion.update(dt);
                    explosion.touchInModels(this.grid);
                } else {
                    //remove
                    //testExplosionEffect(explosion);
                    this.grid.removeModel(explosion);
                    iterator.remove();
                }
            }
        }
    }

    public void update(long dt) {
        long passed = dt - this.timeDropped;

        if (passed < realDuration) {
            updateExplosion(dt);
        } else {
            //bomb terminates
            this.triggered = false;
        }
    }

    public boolean isTriggered() {
        return triggered;
    }

    public long getTimeout() {
        return timeout;
    }

    public Bomberman getBomberman() {
        return bomberman;
    }

    @Override
    public void touchedByExplosion(Explosion explosion) {
        //Nothing happens
    }

    public Grid getGrid() {
        return grid;
    }

    public int getRange() {
        return range;
    }

    public void setExplosions(List<Explosion> explosions) {
        this.explosions = explosions;
    }
}
