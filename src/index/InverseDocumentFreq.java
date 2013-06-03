package index;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InverseDocumentFreq {

	List<String> terms;
	List<Integer> termFreq;
	int numDocs;
	
	public InverseDocumentFreq(){
		numDocs = 0;
		terms = new ArrayList<String>();
		termFreq = new ArrayList<Integer>();
	}
	
	public void addDocument(String docText){
		numDocs++;
		String[] allTerms = docText.split("[\\s]");
		Set<String> uniqueTerms = new HashSet<String>();

		for(String term : allTerms){
			if(!uniqueTerms.contains(term)){
				uniqueTerms.add(term);
				addTerm(term);
			}
		}
	}
	
	public void addTerm(String term){
		int index = terms.indexOf(term);

		if(index != -1){
			int count = termFreq.get(index) + 1;
			termFreq.add(index, count);
		}
		else{
			terms.add(term);
			termFreq.add(1);
		}
	}
	
	public double getIDF(String term){
		int index = terms.indexOf(term);
		
		if(index >= termFreq.size()){
			throw new Error();
		}
		if(index == -1)
			System.out.println("--"+term);
		double idf = (((double)numDocs)/(termFreq.get(index)));
		return Math.abs(Math.log(idf)+1);
	}

}
