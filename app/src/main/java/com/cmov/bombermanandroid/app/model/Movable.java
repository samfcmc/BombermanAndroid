package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.Rect;

import com.cmov.bombermanandroid.app.commands.Command;

import java.util.LinkedList;
import java.util.Queue;

public class Movable extends Model {

    private Queue<Command> receivedCommands;
    private Rect rect;
    private float speed;
    private boolean isDead;

    public Movable(Bitmap bitmap, int x, int y, float speed, boolean isDead){
        super(bitmap, x, y, true);
        this.receivedCommands = new LinkedList<Command>();
        this.speed = speed;
        this.isDead = isDead;
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
