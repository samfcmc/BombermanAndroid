package com.cmov.bombermanandroid.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapLib {

    public static Bitmap getBombBitmap(Context context) {
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.bomb);
    }

    public static Bitmap getRobotBitmap(Context context) {
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);
    }

    public static Bitmap getBombermanBitmap(Context context) {
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.bomberman);
    }

    public static Bitmap getObstacleBitmap(Context context){
        return  BitmapFactory.decodeResource(context.getResources(), R.drawable.obstacle);
    }

    public static Bitmap getWallBitmap(Context context){
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.wall);
    }

    public static Bitmap getBackgroundBitmap(Context context){
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
    }
}
