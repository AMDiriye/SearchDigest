package text;

import java.util.*;
import java.io.*;


 
 public class Bigrams {
 
 	Hashtable table;
 	String bigram;
 	String[] bigramStrings;
 	int[] bigramCounts;
 	BufferedReader inFile;
 	Enumeration elements, keys;
 	BigramsCounts[] fullResults;
 	String text;
 	
 	public Bigrams(String text){
 		this.text = text;
 	}

	public void run() {
		collectStats();
		collectResults();
		showFrequencyResults();
		
		} // run() 
 

 
	void collectStats() {
	
		table = new Hashtable();
		StringTokenizer tokens;
		String token1 = "";
		String token2 = "";
	
		tokens = new StringTokenizer(text);
			
			
			while(tokens.hasMoreTokens()) {
				token2 = tokens.nextToken();				
					bigram = token1 + " " + token2;
					Object item = table.get(bigram);
					if (item != null)
						((int[])item)[0]++;
					else {
						int[] count = {1};
						table.put(bigram,count);
						}
						
					token1 = token2; // step forward
					}
			
			
	 System.out.println("Number of bigrams is: " + table.size());
	
	} // collectStats()
	
/** Local class for bigram count pairs.
*/	
 class BigramsCounts{
 	int count;
 	String bigram;
 	
 	BigramsCounts(int count, String bigram) {
 		this.count = count;
 		this.bigram = bigram;
 		}
 	}
 	 	
	void collectResults() {

		keys = table.keys();
		elements = table.elements();
		int index = 0;
		fullResults = new BigramsCounts[table.size()];
				
		while(keys.hasMoreElements()){
			elements.hasMoreElements();
			fullResults[index++] = 
				new BigramsCounts(
				((int[])elements.nextElement())[0],
				(String)keys.nextElement());
			}
		} // collectResults
	


	void showAlphaResults() {
	
		Comparator comp = new Comparator(){
			public int compare(Object o1, Object o2){
				return (((BigramsCounts)o1).bigram).compareTo(((BigramsCounts)o2).bigram);
				}
		}; // Comparator comp
	
		Arrays.sort(fullResults, comp);
		System.out.println("Results sorted alphabetically:");
		for(int i=0; i < fullResults.length; i++)
			System.out.println(fullResults[i].count + " " + fullResults[i].bigram);
		
	} // showAlphaResults()
	
	void showFrequencyResults() {
	
		Comparator comp = new Comparator(){
			public int compare(Object o1, Object o2){
				int i1 = ((BigramsCounts)o1).count;
				int i2 = ((BigramsCounts)o2).count;
				if(i1 == i2) return 0;
				return ((i1 > i2) ? -1 : +1);
				}				
		}; // Comparator comp
	
		Arrays.sort(fullResults, comp);
		System.out.println("Results sorted by bigram count:");
		for(int i=0; i < fullResults.length; i++)
			System.out.println(fullResults[i].count + " " + fullResults[i].bigram);
	
	
	} 

} 