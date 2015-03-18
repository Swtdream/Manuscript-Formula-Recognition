package model;

import java.util.List;

public class MyStroke {

	private double sx;
	private double sy;
	private List<MyUnit> infos;
	
	public MyStroke(double x, double y, List<MyUnit> in)
	{
		this.sx = x;
		this.sy = y;
		this.infos = in;
	}

	public double getSx() {
		return sx;
	}

	public void setSx(double sx) {
		this.sx = sx;
	}

	public double getSy() {
		return sy;
	}

	public void setSy(double sy) {
		this.sy = sy;
	}

	public List<MyUnit> getInfos() {
		return infos;
	}

	public void setInfos(List<MyUnit> infos) {
		this.infos = infos;
	}
	
	
}
