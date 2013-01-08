package contentminer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	private static int count(String term, String line){

		Pattern pattern = Pattern.compile(" "+term);
		Matcher matcher = pattern.matcher(" "+line.trim());
		int counter = 0;

		while (matcher.find())
			counter++;

		return counter;

	}

	public static void openFileInBrowser(String fileName){
		try {
			java.awt.Desktop.getDesktop().browse(new File(fileName).toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
