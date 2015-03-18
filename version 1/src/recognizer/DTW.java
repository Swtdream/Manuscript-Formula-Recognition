package recognizer;

import java.util.List;

import model.MyStroke;
import model.MyUnit;
import utils.MathUtils;

public class DTW {

	private static double[][] DP = new double[26][26];
	public static double INFINITY = 1000.0f;

	private List<MyStroke> testedStrokes;
	
	static {
		for (int i = 0; i < 26; i++) {
			DP[0][i] = INFINITY;
			DP[i][0] = INFINITY;
		}
	}

	public void setStrokes(List<MyStroke> l)
	{
		testedStrokes = l;
	}
	
	public double dtw(MyStroke s1, MyStroke s2) {
		DP[0][0] = MathUtils.getDistance(s1.getSx(), s1.getSy(), s2.getSx(),
				s2.getSy());
		List<MyUnit> l1 = s1.getInfos();
		List<MyUnit> l2 = s2.getInfos();
		int len1 = l1.size();
		int len2 = l2.size();
		for (int i = 0; i < len1; i++) {
			for (int j = 0; j < len2; j++) {
				double cost = MathUtils.getDistance(l1.get(i).getTheta(), l1
						.get(i).getLength(), l2.get(j).getTheta(), l2.get(j)
						.getLength());
				double pCost = MathUtils.min3(DP[i][j], DP[i][j + 1],
						DP[i + 1][j]);
				DP[i + 1][j + 1] = cost + pCost;
			}
		}
		return DP[len1][len2];
	}
	
	//eliminate List<MyStroke> s1, enhance performance a little bit
	public double getDistance(List<MyStroke> s2) 
	{
		double dis = 0.0f;
		for(int i=0; i<testedStrokes.size(); i++)
		{
			dis += dtw(testedStrokes.get(i), s2.get(i));
		}
		return dis;
	}
	
	// if temp distance is larger than the transferred minimum value, then break.
	public double optimizedDtw(MyStroke s1, MyStroke s2)
	{
		return 0.0f;
	}
	
	public double optimizedGetDistance(List<MyStroke> s2)
	{
		return 0.0f;
	}

}
