package com.cmov.bombermanandroid.app.text;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import com.cmov.bombermanandroid.app.model.Grid;

/**
 *
 */
public class PauseText extends Drawable {
    Paint paint;
    private static final String TEXT = "Paused";

    public PauseText(int color) {
        paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void draw(Canvas canvas) {
        int size = canvas.getHeight() / Grid.HEIGHT;
        float x = canvas.getWidth() / 2;
        float y = canvas.getHeight() / 2;
        paint.setTextSize(size);

        canvas.drawText("Paused", x, y, paint);
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
