package com.cmov.bombermanandroid.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.cmov.bombermanandroid.app.modes.GameMode;
import com.cmov.bombermanandroid.app.threads.EnemiesThread;
import com.cmov.bombermanandroid.app.threads.GameThread;

import java.util.Timer;

public class MainGameSurfaceView extends SurfaceView implements
        SurfaceHolder.Callback {

    private static final String TAG = MainGameSurfaceView.class.getSimpleName();
    private GameThread thread;
    private Timer timer;

    public MainGameSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public MainGameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MainGameSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void init(Context context) {
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);
        // create the game loop thread
        this.thread = new GameThread(getHolder(), this);
        this.timer = new Timer();

        Game.start(context, GameMode.SINGLEPLAYER);

        // make the GamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // at this point the surface is created and
        // we can safely start the game loop
        //setWillNotDraw(false);
        launchThreads();
    }

    private void launchThreads() {
        this.timer.schedule(this.thread, 0, GameThread.INTERVAL);
    }

    private void cancelThreads() {
        this.thread.cancel();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        cancelThreads();
        this.timer.cancel();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Draw the scenario
        Game.draw(canvas);
    }

    public void performDraw(Canvas canvas) {
        onDraw(canvas);
    }

}
