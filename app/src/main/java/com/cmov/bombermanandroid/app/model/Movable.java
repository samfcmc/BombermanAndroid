package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.util.Log;

import com.cmov.bombermanandroid.app.GameThread;
import com.cmov.bombermanandroid.app.commands.Command;

import java.util.LinkedList;
import java.util.Queue;

public class Movable extends Model {

    private Queue<Command> receivedCommands;
    private float speed;
    private boolean isDead;
    private boolean isMoving;

    /*
     * This coordinates will just be used to perform the
     * animated movement...
     */
    private int movingX;
    private int movingY;
    private float deltaX;
    private float deltaY;

    public Movable(Bitmap bitmap, int x, int y, float speed, boolean isDead) {
        super(bitmap, x, y, true);
        this.receivedCommands = new LinkedList<Command>();
        this.speed = speed;
        this.isDead = isDead;
        this.deltaX = 0;
        this.deltaY = 0;
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
        this.isMoving = true;
    }

    public void stopMoving() {
        this.isMoving = false;
        this.movingX = 0;
        this.movingY = 0;
        this.deltaX = 0;
        this.deltaY = 0;
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

    @Override
    protected float getDrawX(Bitmap scaledBitmap) {
        if (this.movingX != 0) {
            this.deltaX += (GameThread.INTERVAL * this.speed * scaledBitmap.getWidth()) / 1000;
            return getRealX(scaledBitmap) + this.movingX * deltaX;
        } else {
            return getRealX(scaledBitmap);
        }
    }

    @Override
    protected float getDrawY(Bitmap scaledBitmap) {
        if (this.movingY != 0) {
            this.deltaY += (GameThread.INTERVAL * this.speed * scaledBitmap.getHeight()) / 1000;
            return getRealY(scaledBitmap) + this.movingY * deltaY;
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
        int newX = getX() + this.movingX;
        int newY = getY() + this.movingY;
        grid.updateGrid(getX(), getY(), newX, newY);
        setX(newX);
        setY(newY);
        stopMoving();
    }

    public void addCommand(Command command) {
        this.receivedCommands.add(command);
    }

    public Command popNextCommand() {
        return this.receivedCommands.remove();
    }

    public boolean commandsQueueIsEmpty() {
        return this.receivedCommands.isEmpty();
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
