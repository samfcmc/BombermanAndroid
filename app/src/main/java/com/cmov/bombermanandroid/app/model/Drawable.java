package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 *
 */
public class Drawable {
    private Bitmap bitmap;    // the actual bitmap
    private Position position;
    private boolean touched;    // if droid is touched/picked up

    public Drawable(Bitmap bitmap, Position position) {
        this.bitmap = bitmap;
        this.touched = false;
        this.position = position;
    }

    public Drawable(Bitmap bitmap) {
        this(bitmap, new Position(0, 0));
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, position.getX() - (bitmap.getWidth() / 2),
                position.getY() - (bitmap.getHeight() / 2), null);
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
        position.setX(x);
        position.setY(y);
    }

    /**
     * Handles the {ACTION_DOWN} event. If the event happens on the
     * bitmap surface then the touched state is set to <code>true</code> otherwise to <code>false</code>
     *
     * @param eventX - the event's X coordinate
     * @param eventY - the event's Y coordinate
     */
    public void handleActionDown(int eventX, int eventY) {
        if (eventX >= (position.getX() - bitmap.getWidth() / 2) && (eventX <= (position.getX() + bitmap.getWidth() / 2))) {
            if (eventY >= (position.getY() - bitmap.getHeight() / 2) && (position.getY() <= (position.getY() + bitmap.getHeight() / 2))) {
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
