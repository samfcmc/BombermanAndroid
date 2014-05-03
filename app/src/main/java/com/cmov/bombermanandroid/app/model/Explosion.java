package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;

public class Explosion extends Model {

    private static final String TAG = Explosion.class.getSimpleName();

    public static final int STATE_ALIVE 	= 0;	// at least the 1st frame has been showed
    public static final int STATE_DEAD 		= 1;	// all frames running out

    private Rect sourceRect;	// the rectangle to be drawn from the animation bitmap
    private int frameNr;		// number of frames in animation
    private int currentFrame;	// the current frame
    private long frameTicker;	// the time of the last frame update
    private int framePeriod;	// milliseconds between each frame (1000/fps)

    private int spriteWidth;	// the width of the sprite to calculate the cut out rectangle
    private int spriteHeight;	// the height of the sprite

    private int state;          // the state of the explosion
    private Bomb bomb;

    public Explosion(Bitmap bitmap, int x, int y, boolean isCollidable, int fps, int frameCount, Bomb bomb) {
        super(bitmap, x, y, isCollidable);
        //dirty hack
        bitmap = scale(bitmap, frameCount);
        super.setBitmap(bitmap);
        this.state = STATE_ALIVE;
        this.currentFrame = 0;
        this.frameNr = frameCount;
        this.spriteWidth = bitmap.getWidth() / frameCount;
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        framePeriod = 1000 / fps;
        frameTicker = 0l;
        this.bomb = bomb;
    }

    public Rect getSourceRect() {
        return sourceRect;
    }
    public void setSourceRect(Rect sourceRect) {
        this.sourceRect = sourceRect;
    }
    public int getFrameNr() {
        return frameNr;
    }
    public void setFrameNr(int frameNr) {
        this.frameNr = frameNr;
    }
    public int getCurrentFrame() {
        return currentFrame;
    }
    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }
    public int getFramePeriod() {
        return framePeriod;
    }
    public void setFramePeriod(int framePeriod) {
        this.framePeriod = framePeriod;
    }
    public int getSpriteWidth() {
        return spriteWidth;
    }
    public void setSpriteWidth(int spriteWidth) {
        this.spriteWidth = spriteWidth;
    }
    public int getSpriteHeight() {
        return spriteHeight;
    }
    public void setSpriteHeight(int spriteHeight) {
        this.spriteHeight = spriteHeight;
    }
    public int getExplosionState() {
        return this.state;
    }
    public boolean isAlive() {
        return this.state == STATE_ALIVE;
    }
    public boolean isDead() {
        return this.state == STATE_DEAD;
    }
    public void setAlive() { this.state = STATE_ALIVE; }
    // the update method for the Explosion
    public void update(long gameTime) {
        if(this.state != STATE_DEAD) {
            if (gameTime > frameTicker + framePeriod) {
                frameTicker = gameTime;
                // increment the frame
                currentFrame++;
                if (currentFrame >= frameNr) {
                    currentFrame = 0;
                    this.state = STATE_DEAD;
                }
            }
            // define the rectangle to cut out sprite
            this.sourceRect.left = currentFrame * spriteWidth;
            this.sourceRect.right = this.sourceRect.left + spriteWidth;
        }
    }

    private Bitmap scale(Bitmap originalBtm, int frames) {
        return Bitmap.createScaledBitmap(originalBtm,
                (int)Grid.TILE_WIDHT * frames, (int)Grid.TILE_HEIGHT, true);
    }
    // the draw method which draws the corresponding frame
    public void draw(Canvas canvas) {
        if(this.state == STATE_DEAD) return;
        // where to draw the sprite
        int realX = getRealX(getScaledBitmap(canvas));
        int realY = getRealY(getScaledBitmap(canvas));
        Rect destRect = new Rect(realX, realY, realX + spriteWidth, realY + spriteHeight);
        canvas.drawBitmap(this.getBitmap(), sourceRect, destRect, null);
    }

    @Override
    protected float getDrawX(Bitmap scaledBitmap) {
        return 0;
    }

    @Override
    protected float getDrawY(Bitmap scaledBitmap) {
        return 0;
    }

    @Override
    public void touchedByExplosion(Explosion explosion) {
        //Explosion should not touch itself... It would be weird
    }

    protected void touchInModels(Grid grid) {
        Cell cell = grid.getCell(getY(), getX());

        for(Model model : cell.getModels()) {
            model.touchedByExplosion(this);
        }
    }

    public Bomb getBomb() {
        return bomb;
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
