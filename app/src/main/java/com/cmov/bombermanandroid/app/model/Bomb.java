package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;

import com.cmov.bombermanandroid.app.BitmapLib;
import com.cmov.bombermanandroid.app.logic.CrossPoint;
import com.cmov.bombermanandroid.app.logic.Point2D;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Bomb extends StaticModel {

    public static final String TAG = Bomb.class.getSimpleName();
    public static final int EXPLOSION_FRAMES = 8;
    Queue<Explosion> explosions;
    private long timeout;
    private Grid grid;
    private long timeDropped;
    private int range;
    private int duration;
    private boolean triggered;
    private long realDuration;
    private Bitmap bitmap;
    private Bomberman bomberman;

    public Bomb(Bitmap bitmap, int x, int y, int range, int duration, long timeout, Grid grid, long timeDropped, Bomberman bomberman) {
        super(bitmap, x, y, false);
        this.range = range;
        this.duration = duration;
        this.timeout = timeout;
        this.grid = grid;
        this.timeDropped = timeDropped;
        this.triggered = true;
        this.realDuration = timeout + (long) duration * 1000;

        this.explosions = new LinkedList<Explosion>();
        this.bitmap = BitmapLib.getBombExplosionBitmap();
        this.bomberman = bomberman;
    }

    void createCrossedExplosion() {
        List<Point2D> calculatedPoints =
                grid.getCrossedPoints(getX(), getY(), range, new CrossPoint());

        for (Point2D point2D : calculatedPoints) {
            Explosion explosion = new Explosion(bitmap,
                    point2D.getX(), point2D.getY(), false,
                    duration, EXPLOSION_FRAMES, this);
            explosions.add(explosion);
            grid.addExplosion(explosion);
        }
    }

    public void createExplosions() {
        //this.explosions = this.crossExplosion.getCrossedExplosion();
        createCrossedExplosion();
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

    public void updade(long dt) {
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
}
