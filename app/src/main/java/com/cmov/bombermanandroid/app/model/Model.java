package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public abstract class Model extends Drawable {

    public static final String TAG = Model.class.getSimpleName();

    private Bitmap bitmap;    // the actual bitmap

    /*
     * Coordinates X and Y in the game grid
     */
    private int x;
    private int y;

    /*
     *  After drawing
     */
    private float lastDrawX;
    private float lastDrawY;

    /*
     *
     */
    private boolean touched;
    private boolean isCollidable;

    private float bitmapWidth;
    private float bitmapHeight;

    public Model(Bitmap bitmap, int x, int y, boolean isCollidable) {
        this.bitmap = bitmap;
        this.touched = false;
        this.x = x;
        this.y = y;
        this.lastDrawX = 0;
        this.lastDrawY = 0;
        this.isCollidable = isCollidable;
    }

    public Model(Bitmap bitmap) {
        this(bitmap, 0, 0, false);
    }

    @Override
    public void draw(Canvas canvas) {
        Bitmap scaled = getScaledBitmap(canvas);
        float drawX = getDrawX(scaled);
        float drawY = getDrawY(scaled);
        canvas.drawBitmap(scaled, drawX, drawY, null);
        afterDraw(drawX, drawY);
    }

    private void afterDraw(float x, float y) {
        this.lastDrawX = x;
        this.lastDrawY = y;
    }

    /*
     * Methods to calculate the coordinates (in the canvas)
     * where the model will be drawn
     */
    protected abstract float getDrawX(Bitmap scaledBitmap);
    protected abstract float getDrawY(Bitmap scaledBitmap);

    //this method calculates the length of each square on
    public void setTileSize(Canvas canvas) {
        Grid.calculateTileSize(canvas.getWidth(),canvas.getHeight());
        setBitmapWidth(Grid.TILE_WIDHT);
        setBitmapHeight(Grid.TILE_HEIGHT);
    }

    public Bitmap getScaledBitmap(Canvas canvas) {
        //this method should be called only once
        setTileSize(canvas);
        Bitmap scaled = Bitmap.createScaledBitmap(this.getBitmap(), (int)bitmapWidth, (int)bitmapHeight, true);
        return scaled;
    }

    public float getBitmapWidth() { return bitmapWidth; }
    public float getBitmapHeight() { return bitmapHeight; }
    public void setBitmapWidth(float bitmapWidth) { this.bitmapWidth = bitmapWidth; }
    public void setBitmapHeight(float bitmapHeight) { this.bitmapHeight = bitmapHeight; }
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

    protected float getLastDrawX() {
        return lastDrawX;
    }

    protected float getLastDrawY() {
        return lastDrawY;
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
