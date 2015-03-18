package model;

import java.awt.Point;

/**
 * Created by yingshuo chen on 2015/3/16.
 */
public class MyPoint {

    public double x;
    public double y;

    public MyPoint(double x, double y) {

        this.x = x;
        this.y = y;
    }

    public MyPoint(int x, int y) {

        this.x = (double)x;
        this.y = (double)y;
    }

    public MyPoint(Point p) {
        this.x = p.getX();
        this.y = p.getY();
    }


    public MyPoint(MyPoint p) {
        this.x = p.x;
        this.y = p.y;
    }

}
