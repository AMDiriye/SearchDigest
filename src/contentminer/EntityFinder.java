package contentminer;


import java.util.ArrayList;

import org.jsoup.nodes.Node;


public class EntityFinder {

	
	public ArrayList<Node> getEntities(String term, String text){
		
		String stemmedTerm = Utilities.stem(Utilities.removeStopWords(term));
		
		getSentences(term, text);
		
		
		return null;
	} 
	
	
	private ArrayList<String> getSentences(String terms, String text){
		
		String[] passages = makePassages(text, "[.][\\s]");
		
		for(String passage : passages)
			System.out.println(passage);
		return null;
	}
	
	
	private String[] makePassages(String text, String delimiter){
		ArrayList<String> passages = new ArrayList<String>();
		String[] tempPassages = text.split(delimiter);
		String tempPassage = "";
		
		for(String passage : tempPassages){
			tempPassage += " " + passage;
			
			if(isSuitablePassage(tempPassage)){
				passages.add(tempPassage);
				tempPassage = "";
			}
			else if(tempPassage.split(" ").length >= 30 && passage.split(" ").length <= 30){
				passages.add(tempPassage.replace(passage, ""));
				passages.add(passage);
				tempPassage = "";
			}
			
			else if(passage.split(" ").length >= 30){
				
				if(!tempPassage.equals(passage))
					passages.add(tempPassage.replace(passage, ""));
				
				String newDelim;
				
				if(delimiter == "[.][\\s]"){
					newDelim = "[,|;|\\n|\\r]";
				}
				else{
					newDelim = " ";
				}
				
				String[] newPassages = makePassages(passage, newDelim);
				
				for(String newPassage : newPassages){
					passages.add(newPassage);
				}
				tempPassage = "";
			}
		}
		
		return passages.toArray(new String[0]);
	}
	
	private boolean isSuitablePassage(String passage){
		int passageLength = passage.split(" ").length;
		
		return passageLength > 5 && passageLength <=30 && passage.length() >25;
	}
}
