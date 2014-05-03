package com.cmov.bombermanandroid.app.logic;

import com.cmov.bombermanandroid.app.model.Explosion;
import com.cmov.bombermanandroid.app.model.Grid;

import java.util.List;


public abstract class ExplosionCalculator {

    //several ways to calculate an explosion
    public abstract List<Explosion> calculateExplosion(int sourceX, int sourceY, int range, Grid grid);

}
