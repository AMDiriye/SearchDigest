package run;
import text.Bigrams;
import text.TextProcessor;
import utilities.Utilities;
import contentalignment.SegmentationFactory;


public class TopTermsExtraction {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SegmentationFactory segmentFactory = new SegmentationFactory("http://gigaom.com/2013/02/02/what-i-learned-at-google-and-microsoft-about-building-better-products/");
		String text = segmentFactory.getDoc().text();
		String[] topKTerms = Utilities.getTopKTerms(text);
		printList(topKTerms);
		
		Bigrams bigrams = new Bigrams(Utilities.stem(Utilities.removeStopWords(text)));
		bigrams.run();

	}

	private static void printList(String[] str){

		if(str == null)
			return;
		
		System.out.println("Top k terms:");
		for(int i=0;i<str.length;i++){
			System.out.println(str[i]+", ");
		}
		System.out.println();
	}

}
