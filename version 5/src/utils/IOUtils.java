package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import model.MySymbol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class IOUtils {
	
	private final static String FILENAME = "dataset";
	private final static String EXTENSION = ".txt";
	
	public static List<MySymbol> jsonFileToObject(int numStroke)
	{
		String content = "";
		content = readFile(FILENAME + numStroke);
		
		Gson gson = new Gson();
		List<MySymbol> symbols = gson.fromJson(content, new TypeToken<List<MySymbol>>(){}.getType());
		return symbols;
	}
	
	public static void objectToJsonFile(int numStroke, List<MySymbol> symbols) 
	{
		Gson gson = new Gson();
		String str = gson.toJson(symbols);
		System.out.println(str);
		writeFile(str, FILENAME + numStroke);
	}
	
	public static void objectToJsonFile(int v, int numStroke, List<MySymbol> symbols) 
	{
		Gson gson = new Gson();
		String str = gson.toJson(symbols);
		System.out.println(str);
		writeFile(str, FILENAME + numStroke + "_" + v);
	}
	
	public static String readFile(String filename)
	{
		String res = "";
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(filename + EXTENSION));
			String line;
			while((line = in.readLine())!=null)
			{
				res += line;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return res;
	}
	
	public static void writeFile(String content, String filename)
	{
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(filename + EXTENSION));
			out.write(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
