package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.util.Log;
import com.cmov.bombermanandroid.app.threads.ExplosionThread;

import java.util.Timer;

public class Bomb extends StaticModel  {

    public static final String TAG = Bomb.class.getSimpleName();

    private int range;
    private int duration;
    private float timeout;
    private Grid grid;
    private Bomberman bomberman;

    private Timer timer;
    private ExplosionThread explosionThread;
   // private List<ExplosionThread> explosionThreads;

    public Bomb(Bitmap bitmap, int x, int y, int range, int duration, int timeout, Grid grid, Bomberman bomberman){
        super(bitmap, x, y, false);
        this.range = range;
        this.duration = duration;
        this.timeout = timeout;
        this.grid = grid;
        this.timer = new Timer();
        this.explosionThread = null;
        this.bomberman = bomberman;
     //   this.explosionThreads = new ArrayList<ExplosionThread>();
    }

    public int getRange() {
        return range;
    }

    public int getDuration() {
        return duration;
    }

    public float getTimeout() {
        return timeout;
    }

    public Grid getGrid() { return this.grid; }

    public Timer getTimer() {
        return timer;
    }


    public void setUpTimer() {

        if(timer != null) {
            timer.cancel();
        }

        timer = new Timer();

        try{
            explosionThread = new ExplosionThread(this);



            timer.schedule(explosionThread, (long) getTimeout() * 1000);
        } catch(Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public void implanted() {
        Log.d(TAG, "Bomb has exploded successfully");
        //TODO: activate another thread here.
    }

    public void updade(long l) {
        if (explosionThread == null || explosionThread.getExplosion() == null) return;

        Explosion explosion = explosionThread.getExplosion();
        if (explosion.isAlive()) {
            explosion.update(l);
        } else {
            //notify bomberman which he's able to implant another bomb
            bomberman.setBomb(null);
        }

    }
}
