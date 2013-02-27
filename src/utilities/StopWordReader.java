package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class StopWordReader {


	public static ArrayList<String> read(String filePath){
		
		ArrayList<String> stopWords = new ArrayList<String>();
		
		try 
		{
			Scanner s = new Scanner(new File(filePath));
			while(s.hasNext())
			{
				String next = s.next();
				if(!next.equals(""))
				{
					stopWords.add(next);
				}
			}
			s.close();
		} catch (FileNotFoundException e) 
		{
			 e.printStackTrace();
		}
		
		return stopWords;
	}

}
