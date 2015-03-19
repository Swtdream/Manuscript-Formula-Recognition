package graphic;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class MyLine {

	public List<Point> pointList;

	public Point lt, rb;

	public MyLine() {
		pointList = new ArrayList<Point>();
	}

	public void addPoint(int x, int y) {
		pointList.add(new Point(x, y));
		if (lt == null && rb == null) {
			lt = new Point(x, y);
			rb = new Point(x, y);
		} else {
			if (x < lt.x)
				lt.x = x;
			if (y < lt.y)
				lt.y = y;
			if (x > rb.x)
				rb.x = x;
			if (y > rb.y)
				rb.y = y;
		}
	}

	public boolean drawable() {
		return (rb.x - lt.x > 2) || (rb.y - lt.y > 4);
	}
	
	public MyRect getRectangle(long id)
	{
		return new MyRect(id, this, true);
	}

	public void draw(Graphics2D g2d) {
		int size = pointList.size();
		int[][] points = new int[2][size];
		for (int i = 0; i < size; i++) {
			points[0][i] = pointList.get(i).x;
			points[1][i] = pointList.get(i).y;
		}
		if (size == 1)
			g2d.drawLine(points[0][0], points[0][1], points[0][0], points[0][1]);
		else
			g2d.drawPolyline(points[0], points[1], size);
	}

}
