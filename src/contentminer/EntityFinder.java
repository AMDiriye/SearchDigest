package contentminer;


import java.util.ArrayList;

import org.jsoup.nodes.Node;


public class EntityFinder {

	
	public ArrayList<Node> getEntities(String term, String text){
		
		String stemmedTerm = Utilities.stem(Utilities.removeStopWords(term));
		String StemmedText = Utilities.stem(Utilities.removeStopWords(text));
		
		
		
		return null;
	} 
	
	
	private ArrayList<String> getSentences(String terms, String text){
		String[] termList = terms.split(" ");
		
		for(String term : termList){
			
		}
		
	} 
}
