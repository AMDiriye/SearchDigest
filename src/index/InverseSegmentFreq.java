package index;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import contentalignment.Cluster;
import contentalignment.Segment;

public class InverseSegmentFreq extends InverseFreq{


	int totalNumSegments;

	public InverseSegmentFreq(List<Cluster> segments)
	{
		terms = new ArrayList<String>();
		termFreq = new ArrayList<Integer>();
		totalNumSegments = numDocs = 0;

		addAllSegments(segments);
	}

	public void addAllSegments(List<Cluster> segments){
		for(Cluster segment : segments){
			totalNumSegments++;
			Set<String> uniqueTerms = new HashSet<String>();
			for(String term : segment.getCleanTextList()){
				if(!uniqueTerms.contains(term)){
					uniqueTerms.add(term);
					addTerm(term);
				}
			}
		}
		numDocs = totalNumSegments;
	} 

}
