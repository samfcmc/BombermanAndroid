package com.cmov.bombermanandroid.app.logic;

import java.util.List;


public interface ExplosionLogic {

    //several ways to calculate the points for an explosion
    public List<Point2D> calculatePoints(int sourceX, int sourceY, int range);

}
