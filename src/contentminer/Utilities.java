package contentminer;

public class Utilities {
	
	static Stemmer stemmer  = new Stemmer();
	static StopWordCollection stopWordCollection = StopWordCollection.getInstance();
	
	public static String stem(String terms){
		return stemmer.stripAffixes(terms);
	}
	
	public static String removeStopWords(String terms){
		return stopWordCollection.removeStopWords(terms);
	}
	
}
