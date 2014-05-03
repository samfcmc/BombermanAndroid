package com.cmov.bombermanandroid.app.logic;

import android.graphics.Bitmap;
import com.cmov.bombermanandroid.app.BitmapLib;
import com.cmov.bombermanandroid.app.GameLoader;
import com.cmov.bombermanandroid.app.model.Bomb;


public abstract class ExplosionCalculator {

    public static final int EXPLOSION_FRAMES = 8;

    private Bomb bomb;
    private Bitmap bitmap;
    private int duration;

    protected ExplosionCalculator(Bomb bomb) {
        this.bomb = bomb;

        this.bitmap = BitmapLib.getBombExplosionBitmap();
        this.duration = GameLoader.getInstance().getSetting(GameLoader.GAME_SETTINGS.ED);
    }

    protected Bomb getBomb() {
        return bomb;
    }

    protected Bitmap getBitmap() {
        return bitmap;
    }

    protected int getDuration() {
        return duration;
    }

    //several ways to calculate an explosion
    public abstract void calculateExplosion();

}
