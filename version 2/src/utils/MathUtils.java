package utils;

import model.MyPoint;

public class MathUtils {

	// return 0-pi
	public static double getAngle(double x, double y)
	{
		double angle = 0.0f;
		angle = Math.acos(x / Math.sqrt(x*x + y*y));
		if(y>0) angle = -angle;
		return angle;
	}
	
	public static double getAngle(MyPoint p, MyPoint q)
	{
		double angle = 0.0f;
        double dx = q.x - p.x;
        double dy = q.y - p.y;
		angle = Math.acos(dx / getDistance(p,q));
		if(dy < 0) angle = -angle;
		return angle;
	}
	
	public static double getDistance(double x1, double x2)
	{
		double dx = x2 - x1;
		return dx;
	}
	
	public static double getDistance(double x1, double y1, double x2, double y2)
	{
		double dx = x2 - x1;
		double dy = y2 - y1;
		return Math.sqrt(dx * dx + dy * dy);
	}

    public static double getDistance(MyPoint p, MyPoint q)
    {
        double dx = q.x - p.x;
        double dy = q.y - p.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
	
	public static double min3(double x, double y, double z)
	{
		return Math.min(x, Math.min(y, z));
	}
	
	
}
