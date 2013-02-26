package similarityalgos;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import contentminer.InverseSegmentFreq;
import contentminer.Stemmer;
import contentminer.StopWordCollection;

public class TextSimilarity extends SimilarityMeasure{


	
	
	public TextSimilarity(){
		
	}
	
	public double cosineSimilarity(List<String> entity1, List<String> entity2, InverseSegmentFreq isf){
		double similarity = 0.0;


		for(String term : entity1)
		{
			similarity += getTFIDF(term, entity2.toString(), isf);
		}

		return 1-Math.cos(similarity / Math.sqrt((entity1.size() * entity2.size())));
	}
	
	
	public double jaccardSimilarity(List<String> entity1, List<String> entity2){
		double similarity = 0.0;

		for(String term : entity1) {
			similarity += count(term, entity2.toString());
		}

		return similarity / (entity1.size() +entity2.size() - similarity);
	}

	
	public double diceSimilarity(List<String> entity1, List<String> entity2){
		double similarity = 0.0;

		for(String term : entity1) {
			similarity += count(term, entity2.toString());
		}

		return similarity / (entity1.size() + entity2.size());
	}

	
	public double euclideanDistance(List<String> entity1, List<String> entity2,  InverseSegmentFreq isf){
		double similarity = 0.0;	
	
		Set<String> allTermDocs = new HashSet<String>();
		
		allTermDocs.addAll(entity1);
		allTermDocs.addAll(entity2);
		

		for(String term : allTermDocs)
		{
			similarity += Math.pow(Math.abs(getTFIDF(term, entity1.toString(), isf)-count(term, entity2.toString())),2);
		}

		return Math.sqrt(similarity);
	}
	
	private int count(String term, String line){

		Pattern pattern = Pattern.compile(" "+term);
		Matcher matcher = pattern.matcher(" "+line.trim());
		int counter = 0;

		while (matcher.find())
			counter++;

		return counter;

	}
	
	private double getTFIDF(String term, String line,  InverseSegmentFreq isf){

		Pattern pattern = Pattern.compile(" "+term);
		Matcher matcher = pattern.matcher(" "+line.trim());
		int tf= 0;

		while (matcher.find())
			tf++;

		double idf = isf.getTermFreq(term);
		return tf * idf;
	}
	
}
