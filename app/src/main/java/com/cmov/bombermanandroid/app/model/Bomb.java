package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
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
        this.realDuration = timeout + (long) duration * 1000;

        this.explosions = new LinkedList<Explosion>();
        this.bitmap = BitmapLib.getBombExplosionBitmap();
    }

    void createCrossedExplosion() {
        List<Point2D> calculedPoints =
                grid.getCrossedPoints(getX(),getY(),range,new CrossPoint());

        for(Point2D point2D : calculedPoints){
            explosions.add(new Explosion(bitmap,
                    point2D.getX(),point2D.getY(),false,
                    duration,EXPLOSION_FRAMES));
        }
    }

    public void createExplosions() {
        //this.explosions = this.crossExplosion.getCrossedExplosion();
        Log.d(TAG, "createExplosion range: " + this.range + " duration: " + this.duration);
        createCrossedExplosion();
    }

    public void updadeExplosion(long dt) {
        long passedTime = dt - this.timeDropped;
        //if the bomb timeout is running out then triggers the bomb!!
        if (passedTime >= this.timeout) {
            Iterator<Explosion> iterator = explosions.iterator();
            while (iterator.hasNext()) {
                Explosion explosion = iterator.next();
                if(explosion.isAlive()) {
                    grid.addExplosion(explosion);
                    explosion.update(dt);
                } else {
                    //remove
                    iterator.remove();
                    Log.d(TAG, "Explosion at (" + explosion.getX() + "," + explosion.getY() + ") was removed.");
                }
            }
        }

    }
    public void updade(long dt) {
        long passed = dt - this.timeDropped;

        if(passed < realDuration) {
            Log.d(TAG, "updating bomb");
            updadeExplosion(dt);
        }else {
        //bomb terminates
        this.triggered = false;
        }
    }

        public boolean isTriggered() {
        return triggered;
    }
}
