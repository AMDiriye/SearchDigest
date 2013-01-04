package contentminer;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {
	
	static Stemmer stemmer  = new Stemmer();
	static StopWordCollection stopWordCollection = StopWordCollection.getInstance();
	
	public static String[] extractEntities(String text){
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
		for(String str : entities){
			if(Integer.parseInt(entityCount.get(count)) > 9)
				System.out.println(str+" -- "+entityCount.get(count));
			count++;
		}
		
		return entities.toArray(new String[0]);
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

	private static int count(String term, String line){
		
	    Pattern pattern = Pattern.compile(" "+term);
	    Matcher matcher = pattern.matcher(" "+line.trim());
	    int counter = 0;
	    
	    while (matcher.find())
	        counter++;
	    
	    return counter;
	    
	}
	
}
