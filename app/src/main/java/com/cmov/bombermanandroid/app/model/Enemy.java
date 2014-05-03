package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;

import java.lang.*;

public abstract class Enemy extends Movable {

    public Enemy(Bitmap bitmap, int x, int y, float speed, boolean isDead){
        super(bitmap, x, y, speed, isDead);
    }

}
