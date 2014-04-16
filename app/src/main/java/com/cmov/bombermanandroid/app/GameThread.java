/**
 * 
 */
package com.cmov.bombermanandroid.app;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.TimerTask;


/**
 * @author impaler
 *
 * The Main thread which contains the game loop. The thread must have access to 
 * the surface view and holder to trigger events every game tick.
 */
public class GameThread extends TimerTask {
	
	private static final String TAG = GameThread.class.getSimpleName();

	// Surface holder that can access the physical surface
	private SurfaceHolder surfaceHolder;
	// The actual view that handles inputs
	// and draws to the surface
	private MainGameSurfaceView gameSurfaceView;
    private int i = 0;

	// flag to hold game state 
	private boolean running;
	public void setRunning(boolean running) {
		this.running = running;
	}

	public GameThread(SurfaceHolder surfaceHolder, MainGameSurfaceView gameSurfaceView) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gameSurfaceView = gameSurfaceView;
	}

	@Override
    public void run() {
        Log.d("Thread", "I am running now " + i++);
        Canvas canvas = null;
        // try locking the canvas for exclusive pixel editing
        // in the surface
        try {
            canvas = this.surfaceHolder.lockCanvas();
            synchronized (surfaceHolder) {
                // update game state
                // render state to the screen
                // draws the canvas on the panel
                this.gameSurfaceView.onDraw(canvas);
            }
        } finally {
            // in case of an exception the surface is not left in
            // an inconsistent state
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }	// end finally
    }

}
