package graphic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class MyRect {

	private static final Color activeColor = Color.green;
	private static final Color inactiveColor = Color.red;
	
	public List<MyLine> lineList;

	public int lx, ly, rx, ry;
	public int width, height;

	public boolean isActive;

	public MyRect(MyLine line, boolean ia) {
		lineList = new ArrayList<MyLine>();
		lineList.add(line);

		lx = line.lt.x;
		ly = line.lt.y;

		rx = line.rb.x;
		ry = line.rb.y;

		width = rx - lx;
		height = ry - ly;

		this.isActive = ia;
	}

	public void join(MyRect another) {
		for (MyLine line : another.lineList) {
			this.lineList.add(line);
		}
		lx = Math.min(this.lx, another.lx);
		ly = Math.min(this.ly, another.ly);
		rx = Math.max(this.rx, another.rx);
		ry = Math.max(this.ry, another.ry);
		width = rx - lx;
		height = ry - ly;
	}

	public boolean isTouched(MyRect another) {
		int cx1 = this.lx + this.rx;
		int cy1 = this.ly + this.ry;
		int cx2 = another.lx + another.rx;
		int cy2 = another.ly + another.ry;
		int dx = this.width + another.width + 20;
		int dy = this.height + another.height + 20;
		return (Math.abs(cx1 - cx2) < dx && Math.abs(cy1 - cy2) < dy);
	}

	public void drawLines(Graphics2D g2d) {
		for (MyLine line : this.lineList) {
			line.draw(g2d);
		}
	}

	public void drawRect(Graphics2D g2d) {
		if (this.isActive) {
			g2d.setColor(activeColor);
			g2d.drawRect(lx-10, ly-10, width+20, height+20);
		}
		else
		{
			g2d.setColor(inactiveColor);
			g2d.drawRect(lx-1, ly-1, width+2, height+2);
		}
	}

	// public ... getData();

}
