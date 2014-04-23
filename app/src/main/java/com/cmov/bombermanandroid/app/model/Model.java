package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

/**
 *
 */
public abstract class Model extends Drawable {
    private Bitmap bitmap;    // the actual bitmap
    private int x;
    private int y;
    private boolean touched;    // if droid is touched/picked up

    public Model(Bitmap bitmap, int x, int y) {
        this.bitmap = bitmap;
        this.touched = false;
        this.x = x;
        this.y = y;
     }

    public Model(Bitmap bitmap) {
        this(bitmap, 0, 0);
    }

    @Override
    public void draw(Canvas canvas) {
        Bitmap scaled = getScaledBitmap(canvas);
        canvas.drawBitmap(scaled, getRealX(scaled), getRealY(scaled), null);
    }

    public Bitmap getScaledBitmap(Canvas canvas) {
        int bitmapWidth = canvas.getWidth() / Grid.WIDTH;
        int bitmapHeight = canvas.getHeight() / Grid.HEIGHT;
        Bitmap scaled = Bitmap.createScaledBitmap(this.bitmap, bitmapWidth, bitmapHeight, false);
        return scaled;
    }

    public int getRealX(Bitmap scaledBitmap) {
        return this.x * scaledBitmap.getWidth();
    }

    public int getRealY(Bitmap scaledBitmap) {
        return this.y * scaledBitmap.getHeight();
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
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
