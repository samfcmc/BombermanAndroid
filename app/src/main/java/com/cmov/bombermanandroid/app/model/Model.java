package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 *
 */
public abstract class Model extends Drawable {
    private Bitmap bitmap;    // the actual bitmap
    private int x;
    private int y;
    private boolean touched;
    private boolean isCollidable;
    private Rect rect;

    public Model(Bitmap bitmap, int x, int y, boolean isCollidable) {
        this.bitmap = bitmap;
        this.touched = false;
        this.x = x;
        this.y = y;
        this.isCollidable = isCollidable;
    }

    public Model(Bitmap bitmap) {
        this(bitmap, 0, 0, false);
    }

    @Override
    public void draw(Canvas canvas) {
        int bitmapWidth = canvas.getWidth() / Grid.WIDTH;
        int bitmapHeight = canvas.getHeight() / Grid.HEIGHT;
        Bitmap scaled = Bitmap.createScaledBitmap(this.bitmap, bitmapWidth, bitmapHeight, false);
        canvas.drawBitmap(scaled, this.x * scaled.getWidth(), this.y * scaled.getHeight(), null);
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

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public boolean isCollidable() {
        return isCollidable;
    }

    public void setCollidable(boolean isCollidable) {
        this.isCollidable = isCollidable;
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
