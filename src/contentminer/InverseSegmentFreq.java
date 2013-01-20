package contentminer;

import java.util.ArrayList;
import java.util.List;

import contentalignment.Segment;

public class InverseSegmentFreq {

	
	List<String> terms;
	List<Integer> termFreq;
	int totalNumSegments;
	
	public InverseSegmentFreq(List<Segment> segments)
	{
		terms = new ArrayList<String>();
		termFreq = new ArrayList<Integer>();
		totalNumSegments = 0;
	
		addAllSegments(segments);
	}
	
	
	public void addAllSegments(List<Segment> segments){
		for(Segment segment : segments){
			
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
		
		return Math.log((totalNumSegments/termFreq.get(index)));
	}
	
	
}
