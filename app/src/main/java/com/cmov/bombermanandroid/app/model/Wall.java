package com.cmov.bombermanandroid.app.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;


public class Wall extends StaticModel {

    public Wall(Bitmap bitmap, int x, int y){
        super(bitmap, x, y, true);
    }

    @Override
    public void touchedByExplosion() {
        //The walls are badass and nothing can destroy them :)
    }
}
