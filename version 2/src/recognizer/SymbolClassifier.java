package recognizer;

import java.util.ArrayList;
import java.util.List;

import model.MyStroke;
import model.MySymbol;

public class SymbolClassifier {

	private DTW myDTW = null;
	private static List<List<MySymbol>> DATASET = null;
	private static double MAX_DIS = 1000.0f;
	
	// read data from 4 dataset files in advance.
	static
	{
		DATASET = new ArrayList<List<MySymbol>>();
//		for(int i=1; i<=4; i++)
//		{
//			DATASET.add(IOUtils.jsonFileToObject(i));
//		}
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
