package com.cmov.bombermanandroid.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapLib {

    static private Bitmap bombBitmap;
    static private Bitmap bombExplosionBitmap;


    public static void loadBomb(Context context) {
        bombBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bomb);
    }

    public static void loadBombExplosion(Context context) {
        bombExplosionBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.bomb_explosion);
    }

    public static Bitmap getBombExplosionBitmap() {
        return bombExplosionBitmap;
    }

    public static Bitmap getBombBitmap() {
        return bombBitmap;
    }

    public static Bitmap getRobotBitmap(Context context) {
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);
    }

    public static Bitmap getBombermanBitmap(Context context) {
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.bomberman);
    }

    public static Bitmap getBombermanBitmap(Context context, int player) {
        //TODO: Add a bitmap for each player number
        return getBombermanBitmap(context);
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

    public static Bitmap getGameOverBitmap(Context context) {
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.gameover);
    }
}
