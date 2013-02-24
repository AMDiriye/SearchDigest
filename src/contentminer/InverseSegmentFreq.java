package contentminer;

import java.util.ArrayList;
import java.util.List;

import contentalignment.Cluster;
import contentalignment.Segment;
;
public class InverseSegmentFreq {

	
	List<String> terms;
	List<Integer> termFreq;
	int totalNumSegments;
	
	public InverseSegmentFreq(List<Cluster> segments)
	{
		terms = new ArrayList<String>();
		termFreq = new ArrayList<Integer>();
		totalNumSegments = 0;
	
		addAllSegments(segments);
	}
	
	
	public InverseSegmentFreq(String _allTerms){
		
		totalNumSegments = 1;
		terms = new ArrayList<String>();
		termFreq = new ArrayList<Integer>();
	
		String[] allTerms = _allTerms.split(" ");
		
		for(String term : allTerms){
			addTerm(term);
		}
	}
	
	public void addAllSegments(List<Cluster> segments){
		for(Cluster segment : segments){
			
			totalNumSegments++;
			
			for(String term : segment.getCleanTextList()){
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
	
	
	public double getTermFreq(String term)
	{
		int index = terms.indexOf(term);
		
		if(index >= termFreq.size()){
			System.out.println("problem");
		}
		
		return Math.log((totalNumSegments/termFreq.get(index)));
	}
	
	
}
