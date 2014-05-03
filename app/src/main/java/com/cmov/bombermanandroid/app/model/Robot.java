package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Robot extends Enemy {

    public Robot(Bitmap bitmap, int x, int y, float speed, boolean isDead, boolean isEnemy) {
        super(bitmap, x, y, speed, isDead, isEnemy);
    }

    /**
     * Detects a collision between two movable objects
     *
     * @param movable
     */
    @Override
    public void touchedByMovable(Movable movable) {
        if( !movable.isEnemy() ){
            movable.die();
        }
    }

}
