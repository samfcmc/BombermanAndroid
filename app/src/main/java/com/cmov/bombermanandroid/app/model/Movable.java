package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.graphics.ColorFilter;

import com.cmov.bombermanandroid.app.commands.CharacterCommand;
import com.cmov.bombermanandroid.app.threads.GameThread;
import com.cmov.bombermanandroid.app.commands.Command;

import java.util.LinkedList;

public class Movable extends Model {

    private CharacterCommand receivedCommand;
    private float speed;
    private boolean isDead;
    private boolean isMoving;

    private static final int MOVE_RIGHT = 1;
    private static final int MOVE_LEFT = -1;
    private static final int MOVE_UP = -1;
    private static final int MOVE_DOWN = 1;

    /*
     * This coordinates will just be used to perform the
     * animated movement...
     */
    private int movingX;
    private int movingY;

    public Movable(Bitmap bitmap, int x, int y, float speed, boolean isDead) {
        super(bitmap, x, y, true);
        this.speed = speed;
        this.isDead = isDead;
        this.movingX = 0;
        this.movingY = 0;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    private void startMoving() {
        this.movingX = 0;
        this.movingY = 0;
        this.isMoving = true;
        commandStarted();
    }

    private void commandStarted() {
        this.receivedCommand = null;
    }

    public void stopMoving() {
        this.movingX = 0;
        this.movingY = 0;
        this.isMoving = false;
    }

    public void cancelMovement() {
        this.isMoving = false;
    }

    public void startMovingToRight() {
        startMoving();
        this.movingX = 1;
    }

    public void startMovingToLeft() {
        startMoving();
        this.movingX = -1;
    }

    public void startMovingToUp() {
        startMoving();
        this.movingY = -1;
    }

    public void startMovingToDown() {
        startMoving();
        this.movingY = 1;
    }

    public int getXAfterMovement() {
        return getX() + this.movingX;
    }

    public int getYAfterMovement() {
        return getY() + this.movingY;
    }

    @Override
    protected float getDrawX(Bitmap scaledBitmap) {
        if (this.movingX != 0) {
            float delta = (GameThread.INTERVAL * this.speed * scaledBitmap.getWidth()) / 1000;
            return getLastDrawX() + this.movingX * delta;
        } else {
            return getRealX(scaledBitmap);
        }
    }

    @Override
    protected float getDrawY(Bitmap scaledBitmap) {
        if (this.movingY != 0) {
            float delta = (GameThread.INTERVAL * this.speed * scaledBitmap.getHeight()) / 1000;
            return getLastDrawY() + this.movingY * delta;
        } else {
            return getRealY(scaledBitmap);
        }
    }

    public boolean shouldStop(Bitmap scaledBitmap) {
        float x = getLastDrawX();
        float y = getLastDrawY();
        int realX = getRealX(scaledBitmap);
        int realY = getRealY(scaledBitmap);

        return (x >= realX + scaledBitmap.getWidth()) ||
                (x <= realX - scaledBitmap.getWidth()) ||
                (y <= realY - scaledBitmap.getHeight()) ||
                (y >= realY + scaledBitmap.getHeight());
    }

    public void stopAndUpdatePosition(Grid grid) {
        int newX = getXAfterMovement();
        int newY = getYAfterMovement();
        grid.updateGrid(getX(), getY(), newX, newY);
        setX(newX);
        setY(newY);
        stopMoving();
    }

    public void setCommand(CharacterCommand command) {
        this.receivedCommand = command;
    }

    public CharacterCommand getCommand() {
        return this.receivedCommand;
    }

    private void commandExecuted() {
        this.receivedCommand = null;
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
