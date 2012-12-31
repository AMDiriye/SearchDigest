package contentminer;

import java.util.ArrayList;

public class StopWordCollection {

	private static final StopWordCollection stopWordCollection = new StopWordCollection();
	private static ArrayList<String> stopWords;
	
	
	private StopWordCollection(){
		loadStopWords();
	}
	
	
	public static StopWordCollection getInstance(){
		return stopWordCollection;
	}
	

	private void loadStopWords(){
		stopWords = StopWordReader.read("./data/stopwords.txt");
	}
	
	public String removeStopWords(String _words){
		
		if(stopWords.size() == 0){
			throw new IllegalStateException("Object not initialised");
		}
			
		String cleanedWords = "";
		String[] words = _words.split(" ");
		
		for(String word:words){
			String lowerCasedWord = word.toLowerCase().replaceAll("[^A-Za-z0-9/s/']*", "");
			
			if(!stopWords.contains(lowerCasedWord)){
				cleanedWords +=" "+ lowerCasedWord;
			}
		}
		return cleanedWords;
	}
	
}
