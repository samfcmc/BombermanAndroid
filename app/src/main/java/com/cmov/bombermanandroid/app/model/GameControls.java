package com.cmov.bombermanandroid.app.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.cmov.bombermanandroid.app.R;

/**
 * Game Controls
 * This class is responsible to draw and process touch events in game controls
 */
public class GameControls {
    Control upControl;
    Control downControl;
    Control leftControl;
    Control rightControl;

    public GameControls(Context context) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.up_arrow);
        upControl = new Control(bitmap);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.down_arrow);
        downControl = new Control(bitmap);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.left_arrow);
        leftControl = new Control(bitmap);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.right_arrow);
        rightControl = new Control(bitmap);
    }

    public void setUpControlPosition(Canvas canvas) {
        int x = canvas.getWidth() / 2;
        int y = canvas.getHeight() - (2 * upControl.getBitmap().getHeight());
        upControl.move(x, y);
    }

    public void setDownControlPosition(Canvas canvas) {
        int x = canvas.getWidth() / 2;
        int y = canvas.getHeight() - downControl.getBitmap().getHeight();
        downControl.move(x, y);
    }

    public void setLeftControlPosition(Canvas canvas) {
        int x = (canvas.getWidth() / 2) - leftControl.getBitmap().getWidth();
        int y = canvas.getHeight() - leftControl.getBitmap().getHeight() - (leftControl.getBitmap().getHeight() / 2);
        leftControl.move(x, y);
    }

    public void setRightControlPosition(Canvas canvas) {
        int x = (canvas.getWidth() / 2) + rightControl.getBitmap().getWidth();
        int y = canvas.getHeight() - rightControl.getBitmap().getHeight() - (rightControl.getBitmap().getHeight() / 2);
        rightControl.move(x, y);
    }

    public void draw(Canvas canvas) {
        setUpControlPosition(canvas);
        setDownControlPosition(canvas);
        setLeftControlPosition(canvas);
        setRightControlPosition(canvas);
        upControl.draw(canvas);
        downControl.draw(canvas);
        leftControl.draw(canvas);
        rightControl.draw(canvas);
    }

}
