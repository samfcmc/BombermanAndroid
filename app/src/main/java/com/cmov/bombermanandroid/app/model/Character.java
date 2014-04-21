package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

import com.cmov.bombermanandroid.app.commands.Command;

import java.util.LinkedList;
import java.util.Queue;

public class Character extends Model {

    private Queue<Command> receivedCommands;
    private float speed;
    private boolean isDead;

    public Character(Bitmap bitmap, int x, int y, float speed, boolean isDead){
        super(bitmap, x, y);
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

}
