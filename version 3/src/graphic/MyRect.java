package graphic;

import parser.Combiner;
import parser.PositionDetector;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class MyRect implements Comparable<MyRect>{

	private long ID;

	private static final Color activeColor = Color.green;
	private static final Color inactiveColor = Color.red;
	
	public List<MyLine> lineList = null;

	public int lx, ly, rx, ry;
	public int width, height;

	public boolean isActive;
	
	private int value = -1;

	private String latex = null; // symbol show on the top-right corner.
	
	public MyRect(long id)
	{
		ID = id;
		latex = new String("");
		lineList = new ArrayList<MyLine>();
		lx = ly = Integer.MAX_VALUE;
		rx = ry = Integer.MIN_VALUE;
		this.isActive = true;
	}
	
	public MyRect(long id, MyLine line, boolean ia) {
		ID = id;
		latex = new String("");
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

	public boolean isMe(long aid)
	{
		return (this.ID == aid);
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
	
	public void join(MyLine mline)
	{
		this.lineList.add(mline);
		lx = Math.min(this.lx, mline.lt.x);
		ly = Math.min(this.ly, mline.lt.y);
		rx = Math.max(this.rx, mline.rb.x);
		ry = Math.max(this.ry, mline.rb.y);
		width = rx - lx;
		height = ry - ly;
	}

	public boolean isTouching(MyRect another) {
		int cx1 = this.lx + this.rx;
		int cy1 = this.ly + this.ry;
		int cx2 = another.lx + another.rx;
		int cy2 = another.ly + another.ry;
		int dx = this.width + another.width + 20;
		int dy = this.height + another.height + 20;
		return (Math.abs(cx1 - cx2) < dx && Math.abs(cy1 - cy2) < dy);
	}

	public long getID() {
		return ID;
	}
	
	public void setLatex(String res)
	{
		this.latex = res;
	}
	
	public String getLatex()
	{
		return this.latex;
	}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
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
		if(!latex.equals("") && latex!=null)
		{
			g2d.setColor(Color.BLACK);
			g2d.drawString(latex, rx, ly);
		}
	}

    @Override
    public int compareTo(MyRect o) {

        if(PositionDetector.isContainedBy(this, o)) {
            return 1;
        } else if(PositionDetector.isContainedBy(o, this)) {
            return -1;
        } else {
            if(PositionDetector.isLeft(this, o)) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    // public ... getData();

}
