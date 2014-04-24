package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Robot extends Enemy {

    public Robot(Bitmap bitmap, int x, int y, float speed) {
        super(bitmap, x, y, speed, true);
    }

    @Override
    public void draw(Canvas canvas) {
       super.draw(canvas);
    }
}
