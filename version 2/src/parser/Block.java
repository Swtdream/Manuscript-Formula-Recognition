package parser;

import graphic.MyRect;

import java.util.ArrayList;
import java.util.List;

public class Block implements Comparable<Block> {

	private float lx, ly, rx, ry;

	private float mx, my;

	public int value;

	public String latex;

	public boolean active;

	public Block(MyRect rect) {
		this.lx = rect.lx;
		this.ly = rect.ly;
		this.rx = rect.rx;
		this.ry = rect.ry;
		this.value = rect.getValue();
		this.latex = rect.getLatex();
		this.mx = (lx + rx) / 2;
		initY();
		this.active = true;
	}

	private void initY() {
		if (value == 18 || value == 21) // j, y.
		{
			this.my = 0.6f * ly + 0.4f * ry;
		} else if (value == 5 || value == 19) { // d, k.
			this.my = 0.4f * ly + 0.6f * ry;
		} else {
			this.my = (ly + ry) / 2;
		}
	}

	public boolean hasUpper(Block another) {
		return another.ry <= this.ly;
	}

	public boolean hasLower(Block another) {
		return another.ly >= this.ry;
	}

	public boolean hasExactlyUpper(Block another) {
		return another.my <= this.ly && another.mx >= this.lx
				&& another.mx <= this.rx;
	}

	public boolean hasExactlyLower(Block another) {
		return another.my >= this.ry && another.mx >= this.lx
				&& another.mx <= this.rx;
	}

	public boolean hasSuperscript(Block another) {
		return (another.my < this.ly || another.ry < this.my) && another.ly <= this.ly
				&& another.lx >= this.rx;
	}

	public boolean hasSubscript(Block another) {
		return (another.my > this.ry || another.ly > this.my) && another.ry >= this.ry
				&& another.lx >= this.rx;
	}

	public boolean hasExactlyRight(Block another) {
		return another.lx >= this.rx && another.my > this.ly && another.my < this.ry;
	}
	
	public boolean hasExactlyRight(List<Block> others)
	{
		for(Block another : others)
		{
			if(this.hasExactlyRight(another))
				return true;
		}
		return false;
	}
	
	public List<Block> getUpperBlocks(List<Block> src)
	{
		List<Block> des = new ArrayList<Block>();
		for(Block t : src)
		{
			if(this.hasUpper(t))
				des.add(t);
		}
		return des;
	}

	public List<Block> getLowerBlocks(List<Block> src)
	{
		List<Block> des = new ArrayList<Block>();
		for(Block t : src)
		{
			if(this.hasLower(t))
				des.add(t);
		}
		return des;
	}	
	
	public boolean isBigSymbol() {
		return value < 3;
	}

	public boolean isFraction() {
		return value == 3;
	}

	public boolean canHaveScripts() {
		return (value >= 4 && value <= 23);
	}

	
	@Override
	public int compareTo(Block b) {
		// TODO Auto-generated method stub
		if (this.lx == b.lx)
			return this.ly <= b.ly ? -1 : 1;
		return this.lx < b.lx ? -1 : 1;
	}

}
