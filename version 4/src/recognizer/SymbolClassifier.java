package recognizer;

import java.util.ArrayList;
import java.util.List;

import model.MyStroke;
import model.MySymbol;
import utils.IOUtils;

public class SymbolClassifier {

	private DTW myDTW = null;
	private static List<List<MySymbol>> DATASET = null;
	private static double MAX_DIS = 1000.0f;
	
	public static String[] valueToLatex = new String[]{
		"\\int", "\\sum", "\\prod", "\\frac", 
		"\\partial", "d", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "\\right)",
		"i", "j", "k", "x", "y", "z", "n",
		"+", "-", "=", "\\infty", "\\left(", 
		"mixed", "?"
	};
	
	
	
	// read data from 4 dataset files in advance.
	static
	{
		DATASET = new ArrayList<List<MySymbol>>();
		for(int i=1; i<=4; i++)
		{
			DATASET.add(IOUtils.jsonFileToObject(i));
		}
	}
	
	public SymbolClassifier()
	{
		myDTW = new DTW();
	}
	
	public synchronized int classify(List<MyStroke> strokes)
	{
		int value = 30; // unknown
		double minDis = MAX_DIS;
		
		int size = strokes.size();
		List<MySymbol> data = DATASET.get(size - 1);
		
		myDTW.setStrokes(strokes);
		
		double tempDis = 0.0f;
		
		for(MySymbol s : data)
		{
			tempDis = myDTW.getDistance(s.getStrokes());
			if(tempDis < minDis)
			{
				minDis = tempDis;
				value = s.getValue();
			}
		}
		
		return value;
	}
	
	
}
