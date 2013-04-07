package utilities;

import index.InverseFreq;
import index.InverseSegmentFreq;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.Entities.EscapeMode;
import org.jsoup.nodes.TextNode;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;

import contentalignment.Cluster;
import contentalignment.Segment;


public class Utilities {

	static Stemmer stemmer  = new Stemmer();
	static StopWordCollection stopWordCollection = StopWordCollection.getInstance();
	public static InverseFreq isf;

	public static String[] getTopKTerms(String text){
		ArrayList<String> entities = new ArrayList<String>();
		ArrayList<String> entityCount = new ArrayList<String>();

		text = stem(removeStopWords(text));
		String[] terms = text.split("[\\s]");


		//Count occurrences
		Map<String, Integer> map = new HashMap<String, Integer>();

		for (String s : terms) {
			if (map.containsKey(s)) {
				map.put(s, map.get(s) + 1);
			} else {
				map.put(s, 1);
			}
		}

		ValueComparator<String, Integer> comparator = new ValueComparator<String, Integer> (map);
		Map<String, Integer> sortedMap = new TreeMap<String, Integer> (comparator);
		sortedMap.putAll(map);

		List<String> sortedList = new ArrayList<String> (sortedMap.keySet());

		return sortedList.toArray(new String[]{});
		//System.out.println(sortedList);

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
			if(term.trim().length() != 0)
				similarity += getTFIDF(term, entity2.toString());
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
	public static int count(String term, String line){

		Pattern pattern = Pattern.compile(" "+term);
		Matcher matcher = pattern.matcher(" "+line.trim());
		int counter = 0;

		while (matcher.find())
			counter++;

		return counter;

	}


	//can sometimes find terms inside the middle of words
	private static double getTFIDF(String term, String line){

		Pattern pattern = Pattern.compile(" "+term);
		Matcher matcher = pattern.matcher(" "+line.trim());
		int tf= 0;

		while (matcher.find())
			tf++;
		
		double idf = isf.getIDF(term);
		return tf * idf;

	}


	public double compareDocs()
	{
		return 0.0;
	}

	public static void openFileInBrowser(String fileName){
		try {
			java.awt.Desktop.getDesktop().browse(new File(fileName).toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Document getDoc(String url)
	{
		Document doc = null;

		try {
			doc = Jsoup.connect(url).get();
			//doc= new Cleaner(Whitelist.relaxed()).clean(Jsoup.connect(url).get());
			//doc.outputSettings().prettyPrint(false);
			//doc.outputSettings().escapeMode(EscapeMode.xhtml);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return doc;
	}


	public static List<double[]> getDistributions(Cluster segment, Cluster segment2) {
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


	private static double[] computeDistribution(List<String> termDoc, Cluster segment)
	{
		double[] distribution = new double[termDoc.size()];
		String segmentText = segment.getProcessedText();

		for(int i=0; i<distribution.length;i++)
		{
			distribution[i] = count(termDoc.get(i),segmentText);
		}


		return distribution;
	}

	public static double jsDivergence(double[] p1, double[] p2){
		double jsDiv = 0.0;
		double[] r = new double[p1.length];
		
		for(int i=0; i<p1.length; i++){
			r[i] = 0.5*(p1[i] + p2[i]);
		}
		
		jsDiv = 0.5 * (klDivergence(p1,r) + klDivergence(p2,r));
		
		return Math.sqrt(jsDiv);
	}
	
	
	public static final double log2 = Math.log(2);
	/**
	 * Returns the KL divergence, K(p1 || p2).
	 *
	 * The log is w.r.t. base 2. <p>
	 *
	 * *Note*: If any value in <tt>p2</tt> is <tt>0.0</tt> then the KL-divergence
	 * is <tt>infinite</tt>. Limin changes it to zero instead of infinite. 
	 * 
	 */
	public static double klDivergence(double[] p1, double[] p2) {


		double klDiv = 0.0;

		for (int i = 0; i < p1.length; ++i) {
			if (p1[i] == 0) { continue; }
			if (p2[i] == 0.0) { continue; } // Limin

			klDiv += p1[i] * Math.log( p1[i] / p2[i] );
		}

		return klDiv / log2; // moved this division out of the loop -DM
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
			similarity += Math.pow(Math.abs(getTFIDF(term, entity1.toString())-count(term, entity2.toString())),2);
		}

		return Math.sqrt(similarity);
	}


	private static class ValueComparator<K, V extends Comparable<V>> implements Comparator<K> {

		Map<K, V> map;

		public ValueComparator(Map<K, V> base) {
			this.map = base;
		}

		@Override
		public int compare(K o1, K o2) {
			return map.get(o2).compareTo(map.get(o1));
		}
	}



	public static boolean isValidNode(Node node){

		System.out.println(node.toString());

		if(node instanceof TextNode)
			return true;


		if(node instanceof Comment || ((Element)node).tagName().equalsIgnoreCase("script") || ((Element)node).tagName().equalsIgnoreCase("style"))
			return false;


		return true;
	}

}


