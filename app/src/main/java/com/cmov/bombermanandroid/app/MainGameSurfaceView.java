/**
 * 
 */
package com.cmov.bombermanandroid.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.cmov.bombermanandroid.app.model.Bomberman;

import com.cmov.bombermanandroid.app.model.GameControls;
import com.cmov.bombermanandroid.app.model.Grid;

import java.util.Timer;

public class MainGameSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {

	private static final String TAG = MainGameSurfaceView.class.getSimpleName();

	private GameThread thread;
    private Timer timer;
    private static final int GAME_THREAD_INTERVAL = 30;
    private GameControls gameControls;
    private Grid grid;


	public MainGameSurfaceView(Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		gameControls = new GameControls(context);
        grid = new Grid(context);
		// create the game loop thread
		thread = new GameThread(getHolder(), this);
        timer = new Timer();
        // load the game settings
        LoadGame instance = LoadGame.getInstance(context, grid);
        instance.loadGameSettings();
        instance.loadGameMap();
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
		timer.schedule(thread, 0, GAME_THREAD_INTERVAL);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
        timer.cancel();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// delegating event handling to the droid
			//bomberman.handleActionDown((int)event.getX(), (int)event.getY());
			
			// check if in the lower part of the screen we exit
			if (event.getY() > getHeight() - 50) {
				timer.cancel();
				((Activity)getContext()).finish();
			} else {
				Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
			}
		} if (event.getAction() == MotionEvent.ACTION_MOVE) {
			// the gestures
			/*if (droid.isTouched()) {
				// the droid was picked up and is being dragged
				droid.setX((int)event.getX());
				droid.setY((int)event.getY());
			}*/
		} if (event.getAction() == MotionEvent.ACTION_UP) {
			// touch was released
			/*if (droid.isTouched()) {
				droid.setTouched(false);
			}*/
		}
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {

        // fills the canvas with black
		canvas.drawColor(Color.BLACK);

		//Draw controls
        //gameControls.draw(canvas);

        //Draw the scenario
        grid.draw(canvas);
	}

}
