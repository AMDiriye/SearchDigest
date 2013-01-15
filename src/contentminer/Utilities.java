package contentminer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import contentalignment.Segment;


public class Utilities {

	static Stemmer stemmer  = new Stemmer();
	static StopWordCollection stopWordCollection = StopWordCollection.getInstance();

	public static String[] getTopKTerms(String text){
		ArrayList<String> entities = new ArrayList<String>();
		ArrayList<String> entityCount = new ArrayList<String>();

		text = stem(removeStopWords(text));

		String[] terms = text.split("[\\s]");

		for(String term : terms){
			if(!entities.contains(term.trim())){
				entities.add(term.trim());
				entityCount.add("1");
			}
			else{
				int count = Integer.parseInt(entityCount.get(entities.indexOf(term.trim())));
				count++;
				entityCount.set(entities.indexOf(term.trim()),count+"");
			}
		}
		int count = 0;

		String[] finalList = new String[entityCount.size()];

		for(String str : entityCount){
			int occurrences = Integer.parseInt(str);
			int pos = 0;

			for(String finalListEntities : entityCount){
				if(Integer.parseInt(finalListEntities) >= occurrences){
					pos++; 
				}
			}

			count++;
			
			finalList[pos] = entities.get(count);
			
			}

		int max = finalList.length < 6? finalList.length:6;
		return Arrays.copyOfRange(finalList,0, max);
	} 


	public static String stem(String terms){
		return stemmer.stripAffixes(terms);
	}

	public static String removeStopWords(String terms){
		return stopWordCollection.removeStopWords(terms);
	}


	public static double overlapSimilarity(List<String> entity1, List<String> entity2) {
		double similarity = 0.0;

		for(String term : entity1) {
			similarity += count(term, entity2.toString());
		}

		return similarity/Math.min(entity1.size(),entity2.size());
	}

	public static double cosineSimilarity(List<String> entity1, List<String> entity2){
		double similarity = 0.0;


		for(String term : entity1)
		{
			similarity += count(term, entity2.toString());
		}

		return 1-Math.cos(similarity / Math.sqrt((entity1.size() * entity2.size())));
	}

	public static double jaccardSimilarity(List<String> entity1, List<String> entity2){
		double similarity = 0.0;

		for(String term : entity1) {
			similarity += count(term, entity2.toString());
		}

		return similarity / (entity1.size() +entity2.size() - similarity);
	}

	public static double diceSimilarity(List<String> entity1, List<String> entity2){
		double similarity = 0.0;

		for(String term : entity1) {
			similarity += count(term, entity2.toString());
		}

		return similarity / (entity1.size() + entity2.size());
	}

	//can sometimes find terms inside the middle of words
	private static int count(String term, String line){

		Pattern pattern = Pattern.compile(" "+term);
		Matcher matcher = pattern.matcher(" "+line.trim());
		int counter = 0;

		while (matcher.find())
			counter++;

		return counter;

	}
	
	public double compareDocs()
	{
		return 0.0;
	}

	public static void openFileInBrowser(String fileName){
		try {
			java.awt.Desktop.getDesktop().browse(new File(fileName).toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Document getDoc(String url)
	{
		Document doc = null;
		
		try {
			doc= Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return doc;
	}


	public static List<double[]> getDistributions(Segment segment, Segment segment2) {
		List<double[]> distributions = new ArrayList<double[]>();
		
		List<String> termsDoc = new ArrayList<String>(); 
		List<String> termsDoc2= new ArrayList<String>();
		Set<String> allTermDocs = new HashSet<String>();
		
		termsDoc.addAll(segment.getCleanTextList());
		termsDoc2.addAll(segment2.getCleanTextList());
		
		allTermDocs.addAll(termsDoc);
		allTermDocs.addAll(termsDoc2);
		
		distributions.add(computeDistribution((new ArrayList<String>(allTermDocs)),segment));
		distributions.add(computeDistribution((new ArrayList<String>(allTermDocs)),segment2));		
		
		return distributions;
	}
	
	
	private static double[] computeDistribution(List<String> termDoc, Segment segment)
	{
		double[] distribution = new double[termDoc.size()];
		String segmentText = segment.getProcessedText();
		
		for(int i=0; i<distribution.length;i++)
		{
			distribution[i] = count(termDoc.get(i),segmentText);
		}
		
		
		return distribution;
	}
	
	public static double calculateKLD(List<String> values,List<String> value2)   
	{  

	    Map<String, Integer> map = new HashMap<String, Integer>();  
	    Map<String, Integer> map2 = new HashMap<String, Integer>();  
	    for (String sequence : values)  
	    {  
	        if (!map.containsKey(sequence))  
	        {  
	            map.put(sequence, 0);
	        }
	        map.put(sequence, map.get(sequence) + 1);
	    }

	    for (String sequence : value2)  
	    {  
	        if (!map2.containsKey(sequence)) {
	            map2.put(sequence, 0);
	        }
	        map2.put(sequence, map2.get(sequence) + 1);
	    }

	    Double result = 0.0;
	    Double frequency2=0.0;
	    for (String sequence : map.keySet())  
	    {

	        Double frequency1 = (double) map.get(sequence) / values.size();
	        System.out.println("Freuency1 "+frequency1.toString());
	        if(map2.containsKey(sequence))
	        {

	            frequency2 = (double) map2.get(sequence) / value2.size();                
	        }
	        result += frequency1 * (Math.log(frequency1/frequency2) / Math.log(2));         
	    }  
	    return result/2.4;  
	}    

	
	public static double euclideanDistance(List<String> entity1, List<String> entity2)
	{
		double similarity = 0.0;	
	
		Set<String> allTermDocs = new HashSet<String>();
		
		allTermDocs.addAll(entity1);
		allTermDocs.addAll(entity2);
		

		for(String term : allTermDocs)
		{
			similarity += Math.pow(Math.abs(count(term, entity1.toString())-count(term, entity2.toString())),2);
		}

		return Math.sqrt(similarity);
	}
}


