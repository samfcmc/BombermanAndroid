package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

/**
 *
 */
public class Wallpaper extends Drawable {
    private Bitmap bitmap;

    public Wallpaper(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    private Bitmap getScaledBitmap(Canvas canvas) {
        Bitmap scaled = Bitmap.createScaledBitmap(this.bitmap, canvas.getWidth(), canvas.getHeight(), true);
        return  scaled;
    }

    @Override
    public void draw(Canvas canvas) {
        Bitmap scaledBitmap = getScaledBitmap(canvas);
        canvas.drawBitmap(scaledBitmap, 0, 0, null);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
