/**
 * 
 */
package com.cmov.bombermanandroid.app.threads;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.cmov.bombermanandroid.app.Game;
import com.cmov.bombermanandroid.app.MainGameSurfaceView;

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

    /*
     * Interval to execute this timer task (in milliseconds)
     */
    public static final int INTERVAL = 30;

	public GameThread(SurfaceHolder surfaceHolder, MainGameSurfaceView gameSurfaceView) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gameSurfaceView = gameSurfaceView;
	}

	@Override
    public void run() {
        Canvas canvas = null;
        // try locking the canvas for exclusive pixel editing
        // in the surface
        try {
            canvas = this.surfaceHolder.lockCanvas();
            if(canvas != null) {
                synchronized (surfaceHolder) {
                    // update game state
                    // render state to the screen
                    // draws the canvas on the panel
                    Game.update(canvas);
                    //this.gameSurfaceView.draw(canvas);
                    this.gameSurfaceView.performDraw(canvas);
                }
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