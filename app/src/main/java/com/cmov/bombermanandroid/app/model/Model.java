package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 *
 */
public abstract class Model extends Drawable {
    private Bitmap bitmap;    // the actual bitmap
    private int x;
    private int y;
    private int tileX;
    private int tileY;
    private boolean touched;    // if droid is touched/picked up

    public Model(Bitmap bitmap, int x, int y) {
        this.bitmap = bitmap;
        this.touched = false;
        this.x = x;
        this.y = y;
        this.tileX = x * bitmap.getWidth();
        this.tileY = y * bitmap.getHeight();
    }

    public Model(Bitmap bitmap) {
        this(bitmap, 0, 0);
    }

    @Override
    public void draw(Canvas canvas) {
       canvas.drawBitmap(bitmap, this.tileX, this.tileY, null);
    }

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Handles the {ACTION_DOWN} event. If the event happens on the
     * bitmap surface then the touched state is set to <code>true</code> otherwise to <code>false</code>
     *
     * @param eventX - the event's X coordinate
     * @param eventY - the event's Y coordinate
     */
    public void handleActionDown(int eventX, int eventY) {
        if (eventX >= (this.x - bitmap.getWidth() / 2) && (eventX <= (this.x + bitmap.getWidth() / 2))) {
            if (eventY >= (this.y - bitmap.getHeight() / 2) && (this.y <= (this.y + bitmap.getHeight() / 2))) {
                // droid touched
                setTouched(true);
            } else {
                setTouched(false);
            }
        } else {
            setTouched(false);
        }
    }


}
