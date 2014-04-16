package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 *
 */
public class Drawable {
    private Bitmap bitmap;	// the actual bitmap
    private int x;			// the X coordinate
    private int y;			// the Y coordinate
    private boolean touched;	// if droid is touched/picked up

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


}
