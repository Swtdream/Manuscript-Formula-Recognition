package model;

import java.util.List;

public class MySymbol {


	private int value;
	
	private List<MyStroke> strokes;
	
    public MySymbol(List<MyStroke> s)
    {
        this.value = 0;
        this.strokes = s;
    }
	
	public MySymbol(int v, List<MyStroke> s)
	{
		this.value = 30; // "?"
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
