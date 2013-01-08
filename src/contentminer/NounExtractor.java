package contentminer;

import java.io.IOException;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class NounExtractor {

	public static void extract(String text){

        // Initialize the tagger
        MaxentTagger tagger;
		try {
			tagger = new MaxentTagger(
			        "./taggers/english-left3words-distsim.tagger");
			
	        // The tagged string
	        String tagged = tagger.tagString(text);
	 
	        // Output the result
	       // System.out.println(tagged);
	        
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 

	}
	
}
