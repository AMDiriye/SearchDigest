package utilities;

import index.InverseDocumentFreq;
import index.InverseSegmentFreq;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
import org.apache.commons.lang3.*;


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
import document.WebPage;


public class Utilities {

	static Stemmer stemmer  = new Stemmer();
	static StopWordCollection stopWordCollection = StopWordCollection.getInstance();
	public static InverseSegmentFreq isf;
	public static InverseDocumentFreq inverseDocFreq;
	
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

	public static double cosineSimilarity(String[] doc1, String[] doc2){
		
		double sumOfTerms = 0.0;
		List<String> terms = new ArrayList<String>(Arrays.asList(doc1));
		terms.retainAll( new ArrayList<String>(Arrays.asList(doc2)));
		
		for(String term : terms){
				//Computes the top half of the cosine eqn
				int termDoc1 = StringUtils.countMatches(Arrays.toString(doc1), term);
				int termDoc2 = StringUtils.countMatches(Arrays.toString(doc2), term);
				sumOfTerms += (Math.log(termDoc1)+1)*(Math.log(termDoc2)+1)*inverseDocFreq.getIDF(term);
		}
		return sumOfTerms/(Math.sqrt(getLogTermFreq(doc1,false))*Math.sqrt(getLogTermFreq(doc2,true)));
	}

	private static double getLogTermFreq(String[] doc, boolean useIDF){
		double logTermFreq = 0;
		double indexTermFreq = 1;
		Set<String> uniqueTerms = new HashSet<String>();
		uniqueTerms.addAll(Arrays.asList(doc));

		for(String term : uniqueTerms){
			if(term.trim().length() == 0)
				continue;
				
			if(useIDF){
				indexTermFreq = inverseDocFreq.getIDF(term);
			}
			logTermFreq += Math.pow((Math.log(StringUtils.countMatches(Arrays.toString(doc), term))+1)*indexTermFreq,2);
		}
		return logTermFreq;
	}
	
	public static double cosineSimilarity(List<String> terms, List<Double> termCounts, List<String> stemmedTerms, List<Double> stemmedTermCounts) {
		double cosSim = 0.0;
		int pos = 0;
		
		for(String term : terms){
			int termIndex = stemmedTerms.indexOf(term);
			if(termIndex != -1){
				cosSim += termCounts.get(pos)*stemmedTermCounts.get(termIndex);
			}
			
			pos++;
		}
		return 1-Math.cos(cosSim / Math.sqrt((terms.size() * stemmedTerms.size())));
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
		
		double idf = inverseDocFreq.getIDF(term);
		return tf * idf;

	}

	public double compareDocs()
	{
		return 0.0;
	}

	public static String getFileContent(String filePath){
		FileInputStream fstream;
		String content = "";
		
		try {
			fstream = new FileInputStream(filePath);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String tempString;
			//Read File Line By Line
			while ((tempString = br.readLine()) != null){
				content +=tempString;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
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

			doc = Jsoup.connect(url).timeout(0).get();
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
		String segmentText = segment.getCleanText();

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

	public static double euclideanDistance(List<String> entity1, List<String> entity2){
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
		if(node instanceof TextNode)
			return true;


		if(node instanceof Comment || ((Element)node).tagName().equalsIgnoreCase("script") || ((Element)node).tagName().equalsIgnoreCase("style"))
			return false;


		return true;
	}

	public static int findWebPageRelationship(WebPage webPage, WebPage webPage2) {
		
		if(webPage2.getURL().contains(webPage.getURL())){
				return 1;
		}
		else if(webPage.getURL().contains(webPage2.getURL())){
				return -1;
		}
		else return 0;
	}

	public static WebPage addTermInfo(WebPage webPage){
		
		String docContent = webPage.getDoc().text();
		docContent = Utilities.removeStopWords(docContent);
		docContent = Utilities.stem(docContent);
		
		HashSet<String> uniqueDocTerms =  new LinkedHashSet<String>(Arrays.asList(docContent.split("[\\s]")));
		String[] allDocTerms = docContent.split("[\\s]");
		
		webPage.setStemmedTerms(Arrays.asList(uniqueDocTerms.toArray(new String[]{})));
		Double[] termFreq = new Double[uniqueDocTerms.size()];
		
		int pos=0;
		for(int i=0;i<allDocTerms.length;i++){
			int posOfTerm = webPage.getStemmedTerms().indexOf(allDocTerms[i]);
			
			if(termFreq[posOfTerm] != null){
				
				termFreq[posOfTerm] = new Double(termFreq[posOfTerm].intValue()+1);
			}
			else{
				termFreq[pos] = new Double(1);
				pos++;
			}		
		}
		webPage.setStemmedTermCounts(Arrays.asList(termFreq));
		return webPage;
	}
	
	

	public static double computeURLSimilarity(String url1, String url2){
		
		double count = 0.0;
		
		String[] urltokens1 = url1.split("[//]");
		String[] urltokens2 = url2.split("[//]");
		
		for(int i=0;i<urltokens1.length;i++){
			for(int j=0;j<urltokens2.length;j++){
				String urltoken1 = lowerCaseAndTrim(urltokens1[i]);
				String urltoken2 = lowerCaseAndTrim(urltokens2[j]);
				if(urltoken1.equals(urltoken2)){
					count++;
				}
			}
		}
		
		double maxLength = urltokens2.length < urltokens1.length?urltokens1.length:urltokens2.length;
		
		return (count/maxLength);
	}

	private static String lowerCaseAndTrim(String str){
		return str.toLowerCase().trim();
	}
	
	 public static int factorial(int n) {
	        int fact = 1; // this  will be the result
	        for (int i = 1; i <= n; i++) {
	            fact *= i;
	        }
	        return fact;
	    }

}


