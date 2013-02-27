package index;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InverseDocumentFreq extends InverseFreq{
	
	
	public InverseDocumentFreq(String _allTerms){

		numDocs = 1;
		terms = new ArrayList<String>();
		termFreq = new ArrayList<Integer>();

		String[] allTerms = _allTerms.split(" ");
		Set<String> uniqueTerms = new HashSet<String>();

		for(String term : allTerms){
			if(!uniqueTerms.contains(term)){
				uniqueTerms.add(term);
				addTerm(term);
			}
		}
	}
		
}
