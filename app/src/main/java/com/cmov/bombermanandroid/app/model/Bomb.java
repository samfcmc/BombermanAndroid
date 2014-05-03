package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import com.cmov.bombermanandroid.app.BitmapLib;
import com.cmov.bombermanandroid.app.logic.CrossPoint;
import com.cmov.bombermanandroid.app.logic.Point2D;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Bomb extends StaticModel  {

    public static final String TAG = Bomb.class.getSimpleName();
    public static final int EXPLOSION_FRAMES = 8;

    private long timeout;
    private Grid grid;
    private long timeDropped;
    private int range;
    private int duration;
    private boolean triggered;
    private boolean explosionOccured;
    private long realDuration;

    Queue<Explosion> explosions;
    private Bitmap bitmap;


    public Bomb(Bitmap bitmap, int x, int y, int range, int duration, long timeout, Grid grid, long timeDropped){
        super(bitmap, x, y, false);
        this.range = range;
        this.duration = duration;
        this.timeout = timeout;
        this.grid = grid;
        this.timeDropped = timeDropped;
        this.triggered = true;
        this.explosionOccured = false; //the bomb has not exploded yet
        this.realDuration = timeout + (long) duration * 1000;

        this.explosions = new LinkedList<Explosion>();
        this.bitmap = BitmapLib.getBombExplosionBitmap();
    }

    void createCrossedExplosion() {
        List<Point2D> calculatedPoints =
                grid.getCrossedPoints(getX(),getY(),range,new CrossPoint());

        for(Point2D point2D : calculatedPoints){
            Explosion explosion = new Explosion(bitmap,
                    point2D.getX(),point2D.getY(),false,
                    duration,EXPLOSION_FRAMES);
            explosions.add(explosion);
            grid.addExplosion(explosion);
        }
    }

    public boolean isExplosionOccured() {
        return explosionOccured;
    }

    public void setExplosionOccured(boolean explosionOccured) {
        this.explosionOccured = explosionOccured;
    }

    public void createExplosions() {
        //this.explosions = this.crossExplosion.getCrossedExplosion();
        Log.d(TAG, "createExplosion range: " + this.range + " duration: " + this.duration);
        createCrossedExplosion();
    }

    public void testExplosionEffect(Explosion explosion){
        if (this.grid.isBomb(explosion.getX(), explosion.getY())) {
            Bomb bomb = (Bomb) grid.getModel(explosion.getX(),explosion.getY());
            bomb.explode();
        } else {
            this.grid.removeCell(explosion);
        }
    }

    private void explode() {
        this.timeout = 0;
    }

    @Override
    public void draw(Canvas canvas) {
        if(!isExplosionOccured()) {
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
                if(explosion.isAlive()) {
                    explosion.update(dt);
                } else {
                    //remove
                    //testExplosionEffect(explosion);
                    grid.removeCell(explosion);
                    iterator.remove();
                    Log.d(TAG, "Explosion at (" + explosion.getX() + "," + explosion.getY() + ") was removed.");
                }
            }
        }

    }
    public void update(long dt) {
        long passed = dt - this.timeDropped;

        if(passed < realDuration) {
            Log.d(TAG, "updating bomb");
            updateExplosion(dt);
        }else {
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
}
