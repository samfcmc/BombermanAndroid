package com.cmov.bombermanandroid.app.logic;

import java.util.List;


public abstract class ExplosionLogic {

    //several ways to calculate the points for an explosion
    public abstract List<Point2D> calculatePoints(int sourceX, int sourceY, int range);

}
