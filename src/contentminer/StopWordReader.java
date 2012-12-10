package contentminer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class StopWordReader {

	
	public static void read(String filePath){
	
		  try 
          {
                  Scanner s = new Scanner(new File(filePath));
                  while(s.hasNext())
                  {
                          String next = s.next();
                          if(!next.equals(""))
                          {
                                  StopWordCollection.addStopWord(next);
                          }
                  }
          } catch (FileNotFoundException e) 
          {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
          }
	
		  
	}
	
}
