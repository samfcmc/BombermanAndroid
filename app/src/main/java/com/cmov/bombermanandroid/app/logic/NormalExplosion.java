package com.cmov.bombermanandroid.app.logic;


import com.cmov.bombermanandroid.app.model.Bomb;
import com.cmov.bombermanandroid.app.model.Explosion;

import java.util.ArrayList;
import java.util.List;

public class NormalExplosion extends ExplosionCalculator {

    private List<Explosion> explosions;

    public NormalExplosion(Bomb bomb) {
        super(bomb);
        this.explosions = new ArrayList<Explosion>();
    }

    private void addExplosion(int x, int y) {
        Explosion explosion = new Explosion(getBitmap(),
                x, y, false,
                getDuration(), EXPLOSION_FRAMES, getBomb());
        explosions.add(explosion);
        //showing explosions to the grid
        getBomb().getGrid().addExplosion(explosion);
    }

    private void addUPoints(int x, int y, int range) {
        int upLimit = y - range;
        int nextY = y;

        while (nextY >= upLimit &&
                !getBomb().getGrid().isWall(x,nextY)) {
           addExplosion(x,nextY);
            nextY -= 1;
        }
    }

    private void addDOWNpoints(int x, int y, int range) {
        int downLimit = y + range;
        int nextY = y;

        while (nextY <= downLimit &&
                !getBomb().getGrid().isWall(x,nextY)) {
            addExplosion(x,nextY);
            nextY += 1;
        }
    }

    private void addRIGHTpoints(int x, int y, int range) {
        int rightLimit = x + range;
        int nextX = x;

        while (nextX <= rightLimit &&
                !getBomb().getGrid().isWall(nextX, y)) {
            addExplosion(nextX, y);
            nextX += 1;
        }
    }

    private void addLEFTpoints(int x, int y, int range) {
        int leftLimit = x - range;
        int nextX = x;

        while (nextX >= leftLimit &&
                !getBomb().getGrid().isWall(nextX,y)) {
            addExplosion(nextX,y);
            nextX -= 1;
        }
    }

    @Override
    public void calculateExplosion() {

        addUPoints(getBomb().getX(), getBomb().getY(), getBomb().getRange());
        addDOWNpoints(getBomb().getX(), getBomb().getY(), getBomb().getRange());
        addRIGHTpoints(getBomb().getX(), getBomb().getY(), getBomb().getRange());
        addLEFTpoints(getBomb().getX(), getBomb().getY(), getBomb().getRange());

        getBomb().setExplosions(this.explosions);
    }
}
