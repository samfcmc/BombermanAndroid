package com.cmov.bombermanandroid.app.threads;

import android.util.Log;
import com.cmov.bombermanandroid.app.BitmapLib;
import com.cmov.bombermanandroid.app.model.Bomb;
import com.cmov.bombermanandroid.app.model.Explosion;

import java.util.TimerTask;

public class ExplosionThread extends TimerTask {

    public static final String TAG = ExplosionThread.class.getSimpleName();
    public static final int EXPLOSION_FPS = 4;
    public static final int EXPLOSION_FRAMES = 8;

    private Bomb bomb;
    private Explosion explosion;

    public ExplosionThread(Bomb bomb) {
        this.bomb = bomb;
        this.explosion = null;
    }

    public Explosion getExplosion() {
        return explosion;
    }

    @Override
    public void run() {
        Log.d(TAG, "Explosion in action");
        // create explosion
        explosion = new Explosion(BitmapLib.getBombExplosionBitmap(),
                bomb.getX(), bomb.getY(), true, EXPLOSION_FPS, EXPLOSION_FRAMES);
        // add explosion to the grid
        this.bomb.getGrid().addExplosion(explosion);
        //now the bomber man can implant another bomb
        this.bomb.implanted();
    }
}
