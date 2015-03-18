package graphic;

import recognizer.Result;

public class Block {

	public static enum TYPE { BIG_SYM, DER_NUM_RB, LETTER, ALONE, };
	
	private int lx, ly, rx, ry;
	
	private int value;
	
	private TYPE type;
	
	private String latex;
	
	public Block(MyRect rect, Result res)
	{
		this.lx = rect.lx;
		this.ly = rect.ly;
		this.rx = rect.rx;
		this.ry = rect.ry;
		this.value = res.classValue;
		this.latex = res.latexValue;
		this.type = getType(this.value);
	}
	
	public TYPE getType(int v)
	{
		if(v <= 3)
			return TYPE.BIG_SYM;
		else if(v <= 16)
			return TYPE.DER_NUM_RB;
		else if(v <= 23)
			return TYPE.LETTER;
		else
			return TYPE.ALONE;
	}
	
	public boolean isUpper(Block another)
	{
		return false;
	}
	
	public boolean isLower(Block another)
	{
		return false;
	}
	
	public boolean isSuperscript(Block another)
	{
		return false;
	}
	
	public boolean isSubscript(Block another)
	{
		return false;
	}
	
	public boolean isRight(Block another)
	{
		return false;
	}
	
}
