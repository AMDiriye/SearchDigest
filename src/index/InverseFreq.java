package index;

import java.util.List;

public abstract class InverseFreq {

	List<String> terms;
	List<Integer> termFreq;
	int numDocs;
	
	
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
		
		double idf = (((double)numDocs)/(termFreq.get(index)+1));
		return Math.abs(Math.log(idf));
	}

}
