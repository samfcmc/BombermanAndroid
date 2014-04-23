package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Robot extends Enemy {

    public Robot(Bitmap bitmap, int x, int y, float speed, boolean isDead) {
        super(bitmap, x, y, speed, isDead);
    }

}
