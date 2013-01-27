package contentalignment;

import java.util.ArrayList;
import java.util.List;

import contentminer.Utilities;

public class Cluster {

	List<Segment> segments;
	List<String> terms;
	
	public Cluster(){
		this.segments = new ArrayList<Segment>();
		this.terms = new ArrayList<String>();
	}
	
	public double getSimilarity(Segment segment)
	{
		return Utilities.cosineSimilarity(segment.getCleanTextList(),terms);
	}
	

	public void addSegment(Segment segment){
		segments.add(segment);
		terms.addAll(segment.cleanTextList);
	}
}
