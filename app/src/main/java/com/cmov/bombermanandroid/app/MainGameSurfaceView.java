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

import java.util.Timer;

public class MainGameSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {

	private static final String TAG = MainGameSurfaceView.class.getSimpleName();

	private GameThread thread;
    private Timer timer;
    private Bomberman bomberman;
    private static final int GAME_THREAD_INTERVAL = 30;

	public MainGameSurfaceView(Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		// create bomberman and load bitmap
        bomberman = new Bomberman(BitmapFactory.decodeResource(getResources(), R.drawable.bomberman), 50, 50, 3, 1,false);
		
		// create the game loop thread
		thread = new GameThread(getHolder(), this);
        timer = new Timer();
		
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
		thread.setRunning(true);
        //setup main activity to run 30 fps
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
			bomberman.handleActionDown((int)event.getX(), (int)event.getY());
			
			// check if in the lower part of the screen we exit
			if (event.getY() > getHeight() - 50) {
				thread.setRunning(false);
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
		canvas.drawColor(Color.GRAY);
		bomberman.draw(canvas);
		//droid.draw(canvas);
	}

}
