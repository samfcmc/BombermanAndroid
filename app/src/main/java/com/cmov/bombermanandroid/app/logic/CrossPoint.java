package com.cmov.bombermanandroid.app.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

//This class calculates cross explosion
public class CrossPoint extends ExplosionLogic {

    public static final int N = 0; //North
    public static final int S = 1; //South
    public static final int W = 2; //West
    public static final int E = 3; //East

    private List<List<Point2D>> axisPoints;
    private int sourceX;
    private int sourceY;

    public CrossPoint() {
        this.axisPoints = new ArrayList<List<Point2D>>(4);
        this.axisPoints.add(new ArrayList<Point2D>());
        this.axisPoints.add(new ArrayList<Point2D>());
        this.axisPoints.add(new ArrayList<Point2D>());
        this.axisPoints.add(new ArrayList<Point2D>());
    }

    private void addAllPoints(List<Point2D> lst) {
        for (int i = 0; i < axisPoints.size(); i++) {
            for (Point2D point2D : this.axisPoints.get(i)){
                lst.add(point2D);
            }
        }
    }

    public boolean isValidPosition(int posX, int posY) {
        return posX >= 0 && posY >= 0;
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


    @Override
    public List<Point2D> calculatePoints(int sourceX, int sourceY, int range) {
        assert range > 0;
        assert sourceX >= 0;
        assert sourceY >= 0;

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
}
