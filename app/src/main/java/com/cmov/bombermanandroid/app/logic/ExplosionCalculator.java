package com.cmov.bombermanandroid.app.logic;

import com.cmov.bombermanandroid.app.model.Bomb;


public abstract class ExplosionCalculator {

    private Bomb bomb;

    protected ExplosionCalculator(Bomb bomb) {
        this.bomb = bomb;
    }

    protected Bomb getBomb() {
        return bomb;
    }

    //several ways to calculate an explosion
    public abstract void calculateExplosion();

}
