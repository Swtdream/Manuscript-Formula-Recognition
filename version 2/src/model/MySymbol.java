package model;

import java.util.List;

public class MySymbol {


	private int value;
	
	private List<MyStroke> strokes;
	
	public MySymbol(int v, List<MyStroke> s)
	{
		this.value = v;
		this.strokes = s;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public List<MyStroke> getStrokes() {
		return strokes;
	}

	public void setStrokes(List<MyStroke> strokes) {
		this.strokes = strokes;
	}
	
}
