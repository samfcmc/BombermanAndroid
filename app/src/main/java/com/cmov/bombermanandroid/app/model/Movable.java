package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;

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
        super(bitmap, x, y);
        this.receivedCommands = new LinkedList<Command>();
        this.speed = speed;
        this.isDead = isDead;
        this.isMoving = false;
        this.movingX = 0;
        this.movingY = 0;
        this.deltaX = 0;
        this.deltaY = 0;

    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    private void startMoving() {
        this.isMoving = true;
        this.deltaX = 0;
        this.deltaY = 0;
        this.movingX = 0;
        this.movingY = 0;
    }

    private void stopMoving() {
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

    public void draw(Canvas canvas) {
        if (this.isMoving) {
            Bitmap scaled = getScaledBitmap(canvas);
            this.deltaX += (GameThread.INTERVAL * this.speed * scaled.getWidth()) / 1000;
            this.deltaY += (GameThread.INTERVAL * this.speed * scaled.getHeight()) / 1000;
            float posX = getRealX(scaled) + this.movingX * deltaX;
            float posY = getRealY(scaled) + this.movingY * deltaY;
            canvas.drawBitmap(scaled, posX, posY, null);

            //Check if we must stop the movement
            if (shouldStop(posX, posY, scaled)) {
                stopMoving();
                updatePositionAfterMovement();
            }
        } else {
            super.draw(canvas);
        }
    }

    private boolean shouldStop(float x, float y, Bitmap scaledBitmap) {
        return (x >= getX() * scaledBitmap.getWidth() + scaledBitmap.getWidth()) ||
                (x <= getX() * scaledBitmap.getWidth() - scaledBitmap.getWidth()) ||
                (y <= getY() * scaledBitmap.getHeight() - scaledBitmap.getHeight()) ||
                (y >= getY() * scaledBitmap.getHeight() + scaledBitmap.getHeight());
    }

    private void updatePositionAfterMovement() {
        setX(getX() + this.movingX);
        setY(getY() + this.movingY);
    }

    @Override
    public void setAlpha(int i) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
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

}
