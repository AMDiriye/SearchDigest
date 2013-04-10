package utilities;

import net.sf.classifier4J.summariser.*;

public class WebSummaryFactory {

	
	//TODO: makePageSegmentations
	//TODO: makePageSummarization
	//TODO: makePageStructure
	//TODO: makeRelatedPages
	//TODO: makeMap
	//TODO: makeForms
	//TODO: makeQueryRepresentation
	//TODO: makeWithinSiteSearch

	
	public static void main(String aegs[]){
		SimpleSummariser s = new SimpleSummariser();
		String input = "The Vector Classifier is an implementation of IClassifier that uses the vector space search algorithm This algorithm is quite fast (compared to the Bayesian algorithm) and does not require training of non-matches. It also has the advantage that its match ratings (as returned by ther classify method) are fairly well distriubuted unlike the Bayesian Classifier which tended to return 0.99 or 0.01. This characteristic makes it ideally suited for categorization type tasks.";
		String summ = s.summarise(input, 1);
		System.out.println(summ);
	}
}
