package contentminer;

import java.util.ArrayList;

public class StopWordCollection {

	public static ArrayList<String> stopWords = new ArrayList<String>();
	
	
	public StopWordCollection(){
		
	}
	
	public static void addStopWord(String stopWord){
		stopWords.add(stopWord);
	}
	
	public static String[] removeStopWords(String _words){
		
		if(stopWords.size() == 0){
			throw new IllegalStateException("Object not initialised");
		}
			
		ArrayList<String> cleanedWords = new ArrayList<String>();
		String[] words = _words.split(" ");
		
		for(String word:words){
			if(!stopWords.contains(word)){
				cleanedWords.add(word);
			}
		}
		return (String[]) cleanedWords.toArray();
	}
	
}
