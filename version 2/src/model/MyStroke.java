package model;

import java.util.List;

public class MyStroke {

	private int sx;
	private int sy;
	private List<MyUnit> infos;
	
	public MyStroke(int x, int y, List<MyUnit> in)
	{
		this.sx = x;
		this.sy = y;
		this.infos = in;
	}

	public int getSx() {
		return sx;
	}

	public void setSx(int sx) {
		this.sx = sx;
	}

	public int getSy() {
		return sy;
	}

	public void setSy(int sy) {
		this.sy = sy;
	}

	public List<MyUnit> getInfos() {
		return infos;
	}

	public void setInfos(List<MyUnit> infos) {
		this.infos = infos;
	}
	
	
}
