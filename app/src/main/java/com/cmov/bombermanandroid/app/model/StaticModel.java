package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.graphics.ColorFilter;

/**
 * Created by samuel on 4/23/14.
 */
public class StaticModel extends Model {


    public StaticModel(Bitmap bitmap, int x, int y, boolean isCollidable) {
        super(bitmap, x, y, isCollidable);
    }

    public StaticModel(Bitmap bitmap) {
        super(bitmap);
    }

    @Override
    protected float getDrawX(Bitmap scaledBitmap) {
        return getRealX(scaledBitmap);
    }

    @Override
    protected float getDrawY(Bitmap scaledBitmap) {
        return getRealY(scaledBitmap);
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
