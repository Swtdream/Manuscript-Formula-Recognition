package model;

public class MyUnit {

	private double theta;
	private double length;
	
	public MyUnit(double t, double l)
	{
		this.theta = t;
		this.length = l;
	}

	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}
	
}
