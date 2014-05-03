package com.cmov.bombermanandroid.app.logic;

import android.graphics.Bitmap;
import com.cmov.bombermanandroid.app.BitmapLib;
import com.cmov.bombermanandroid.app.GameLoader;
import com.cmov.bombermanandroid.app.model.Bomb;
import com.cmov.bombermanandroid.app.model.Explosion;
import com.cmov.bombermanandroid.app.model.Grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

//This class calculates cross explosion
public class CrossedExplosion extends ExplosionCalculator {

    public static final int EXPLOSION_FRAMES = 8;

    public static final int N = 0; //North
    public static final int S = 1; //South
    public static final int W = 2; //West
    public static final int E = 3; //East

    private List<List<Point2D>> axisPoints;
    private int sourceX;
    private int sourceY;

    private Bitmap bitmap;
    private int duration;

    public CrossedExplosion(Bomb bomb) {
        super(bomb);

        this.axisPoints = new ArrayList<List<Point2D>>(4);
        this.axisPoints.add(new ArrayList<Point2D>());
        this.axisPoints.add(new ArrayList<Point2D>());
        this.axisPoints.add(new ArrayList<Point2D>());
        this.axisPoints.add(new ArrayList<Point2D>());

        this.bitmap = BitmapLib.getBombExplosionBitmap();
        this.duration = GameLoader.getInstance().getSetting(GameLoader.GAME_SETTINGS.ED);
    }

    private void addAllPoints(List<Point2D> lst) {
        for (List<Point2D> axisPoint : axisPoints) {
            for (Point2D point2D : axisPoint) {
                lst.add(point2D);
            }
        }
    }

    //A position is valid if 0 < p < GridBound.MAX
    public boolean isValidPosition(int posX, int posY) {
        return posX >= 0 && posX < Grid.WIDTH &&
                posY >= 0 && posY < Grid.HEIGHT;
    }

    private void addPoint(int i, int inc, boolean xx) {

        int x, y;

        x = this.sourceX;
        y = this.sourceY;

        if(xx) {
            x += inc;
        } else {
            y += inc;
        }
        System.out.println(x + " " + y);

        if (!isValidPosition(x,y)) return;

        List<Point2D> lstAux = this.axisPoints.get(i);

        if(lstAux.isEmpty()) {
            lstAux.add(new Point2D(x,y));
        } else {
            Point2D point2D = lstAux.get(lstAux.size()-1);
            if(isValidInc(point2D.getX(), point2D.getY(), inc, xx)) {
                addInAxis(lstAux, point2D.getX(), point2D.getY(), inc, xx);
            }
        }
    }

    private boolean isValidInc(int x, int y, int inc, boolean xx) {
        if(xx) {
            return isValidPosition(x+inc,y);
        }
        return isValidPosition(x,y+inc);
    }

    private void addInAxis(List<Point2D> lstAux, int x, int y, int inc, boolean xx) {
        if(xx) {
            lstAux.add(new Point2D(x+inc,y));
        } else {
            lstAux.add(new Point2D(x,y+inc));
        }
    }

    public static String toString(Collection<Point2D> persons) {
        Iterator<Point2D> it = persons.iterator();
        if (!it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (;;) {
            Point2D e = it.next();
            sb.append(e);
            if (!it.hasNext())
                return sb.append(']').toString();
            sb.append('&').append(' ');
        }
    }

    private List<Point2D> calculatePoints(int sourceX, int sourceY, int range) {
        this.sourceX = sourceX;
        this.sourceY = sourceY;

        List<Point2D> result = new ArrayList<Point2D>();
        //adding the source point
        result.add(new Point2D(sourceX,sourceY));

        while (range > 0) {
            addPoint(N, 1, true);
            addPoint(S, -1, true);
            addPoint(E,1, false);
            addPoint(W,-1,false );
            range--;
        }
        addAllPoints(result);
        return result;
    }


    @Override
    public void calculateExplosion() {
        List<Explosion> explosions = new ArrayList<Explosion>();

        for(Point2D point : this.calculatePoints(getBomb().getX(),
                getBomb().getY(), getBomb().getRange())) {

            if(!getBomb().getGrid().isWall(point.getX(),point.getY())) {
                Explosion explosion = new Explosion(bitmap,
                        point.getX(), point.getY(), false,
                        duration, EXPLOSION_FRAMES, getBomb());

                explosions.add(explosion);
                //showing explosions to the grid
                getBomb().getGrid().addExplosion(explosion);
            }
        }

        getBomb().setExplosions(explosions);
    }
}
