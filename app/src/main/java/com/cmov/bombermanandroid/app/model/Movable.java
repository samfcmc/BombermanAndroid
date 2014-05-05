package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.graphics.ColorFilter;

import com.cmov.bombermanandroid.app.commands.CharacterCommand;
import com.cmov.bombermanandroid.app.threads.GameThread;

/**
 * The type Movable.
 */
public abstract class Movable extends Model {

    private static final int MOVE_RIGHT = 1;
    private static final int MOVE_LEFT = -1;
    private static final int MOVE_UP = -1;
    private static final int MOVE_DOWN = 1;
    private CharacterCommand receivedCommand;
    private float speed;
    private boolean isDead;
    private boolean isEnemy;
    private boolean isMoving;
    /*
     * This coordinates will just be used to perform the
     * animated movement...
     */
    private int movingX;
    private int movingY;
    private int stopX;
    private int stopY;

    /**
     * Instantiates a new Movable.
     *
     * @param bitmap the bitmap
     * @param x      the x
     * @param y      the y
     * @param speed  the speed
     * @param isDead the is dead
     */
    public Movable(Bitmap bitmap, int x, int y, float speed, boolean isDead, boolean isEnemy) {
        super(bitmap, x, y, false);
        this.speed = speed;
        this.isDead = isDead;
        this.isEnemy = isEnemy;
        this.movingX = 0;
        this.movingY = 0;
        this.stopX = 0;
        this.stopY = 0;
    }

    /**
     * Is enemy
     *
     * @return
     */
    public boolean isEnemy() {
        return isEnemy;
    }

    /**
     * Is moving.
     *
     * @return the boolean
     */
    public boolean isMoving() {
        return isMoving;
    }

    /**
     * Sets moving.
     *
     * @param isMoving the is moving
     */
    public void setMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    private void tryStartMoving(int movingX, int movingY) {
        /*
         * If it's the same direction should not be in the same way
         * If it's already moving and the command it's the same we should
         * execute it in the future and wait for the current one finishes first
         */
        if (wantsToMoveInSameDirection(movingX, movingY) && !wantsToMoveInSameWay(movingX, movingY)) {
            startMoving(movingX, movingY, getX(), getY());
        } else if (!this.isMoving) {
            startMoving(movingX, movingY, getX() + movingX, getY() + movingY);
        }
    }

    private void startMoving(int movingX, int movingY, int stopX, int stopY) {
        this.stopX = stopX;
        this.stopY = stopY;
        this.movingX = movingX;
        this.movingY = movingY;
        this.isMoving = true;
        commandStarted();
    }

    private boolean wantsToMoveInSameDirection(int movingX, int movingY) {
        return (isMovingInHorizontal() && movingX != 0) || (isMovingInVertical() && movingY != 0);
    }

    private boolean wantsToMoveInSameWay(int movingX, int movingY) {
        return (this.movingX == movingX) && (this.movingY == movingY);
    }

    private void commandStarted() {
        this.receivedCommand = null;
    }

    /**
     * Stop moving.
     */
    public void stopMoving() {
        this.movingX = 0;
        this.movingY = 0;
        this.isMoving = false;
    }

    /**
     * Cancel movement.
     */
    public void cancelMovement() {
        this.isMoving = false;
    }

    /**
     * Start moving to right.
     */
    public void startMovingToRight() {
        tryStartMoving(MOVE_RIGHT, 0);
    }

    /**
     * Start moving to left.
     */
    public void startMovingToLeft() {
        tryStartMoving(MOVE_LEFT, 0);
    }

    /**
     * Start moving to up.
     */
    public void startMovingToUp() {
        tryStartMoving(0, MOVE_UP);
    }

    /**
     * Start moving to down.
     */
    public void startMovingToDown() {
        tryStartMoving(0, MOVE_DOWN);
    }

    private boolean isMovingInHorizontal() {
        return this.movingX != 0;
    }

    private boolean isMovingInVertical() {
        return this.movingY != 0;
    }

    /**
     * Gets x after movement.
     *
     * @return the x after movement
     */
    public int getXAfterMovement() {
        return this.stopX;
    }

    /**
     * Gets y after movement.
     *
     * @return the y after movement
     */
    public int getYAfterMovement() {
        return this.stopY;
    }

    @Override
    protected float getDrawX(Bitmap scaledBitmap) {
        if (isMovingInHorizontal()) {
            float delta = (GameThread.INTERVAL * this.speed * scaledBitmap.getWidth()) / 1000;
            return getLastDrawX() + this.movingX * delta;
        } else {
            return getRealX(scaledBitmap);
        }
    }

    @Override
    protected float getDrawY(Bitmap scaledBitmap) {
        if (isMovingInVertical()) {
            float delta = (GameThread.INTERVAL * this.speed * scaledBitmap.getHeight()) / 1000;
            return getLastDrawY() + this.movingY * delta;
        } else {
            return getRealY(scaledBitmap);
        }
    }

    @Override
    public void touchedByExplosion(Explosion explosion) {
        die();
        afterExplosion(explosion);
    }

    public abstract void afterExplosion(Explosion explosion);

    /**
     * Should stop.
     *
     * @param scaledBitmap the scaled bitmap
     * @return the boolean
     */
    public boolean shouldStop(Bitmap scaledBitmap) {
        float x = this.movingX * getLastDrawX();
        float y = this.movingY * getLastDrawY();
        int toStopX = this.movingX * this.stopX * scaledBitmap.getWidth();
        int toStopY = this.movingY * this.stopY * scaledBitmap.getHeight();

        return ((x >= toStopX) && isMovingInHorizontal()) ||
                ((y >= toStopY) && isMovingInVertical());
    }

    /**
     * Stop and updateGameState position.
     *
     * @param grid the grid
     */
    public void stopAndUpdatePosition(Grid grid) {
        grid.updateGrid(getX(), getY(), this.stopX, this.stopY, this);
        setX(this.stopX);
        setY(this.stopY);
        stopMoving();
    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public CharacterCommand getCommand() {
        return this.receivedCommand;
    }

    /**
     * Sets command.
     *
     * @param command the command
     */
    public void setCommand(CharacterCommand command) {
        this.receivedCommand = command;
    }

    private void commandExecuted() {
        this.receivedCommand = null;
    }

    public void die() {
        this.isDead = true;
    }

    /**
     * Detects a collision between two movable objects
     *
     * @param movable
     */
    public abstract void touchedByMovable(Movable movable);

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

    public boolean isDead() {
        return isDead;
    }
}
