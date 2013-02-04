package contentalignment;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;

import contentminer.Utilities;

public class Cluster {

	List<Segment> segments;
	List<String> terms;
	String processedTerms;
	
	public Cluster(){
		this.segments = new ArrayList<Segment>();
		this.terms = new ArrayList<String>();
		this.processedTerms = "";
	}
	
	public double getSimilarity(Segment segment)
	{
		return Utilities.cosineSimilarity(segment.getCleanTextList(),terms);
	}
	

	public void addSegment(Segment segment){
		segments.add(segment);
		terms.addAll(segment.cleanTextList);
		processedTerms += segment.processedTerms;
	}
	
	public boolean isSameCluster(Segment segment){
		
		if(segments.size() < 2)
			return true;
		else{
			if(segment.node instanceof Element)
			{
				//get the first element and see of they are the same
				if(isSameType((Element) segments.get(1).node, (Element) segment.node))
					return true;
				
				else return false;
			}
			else{
				return true;
			}
		}
	}
	
	
	private boolean isSameType(Element elem1, Element elem2){
		
		if(elem1.tagName() != elem2.tagName())
			return false;
		
		else if(!elem1.attr("style").equalsIgnoreCase(elem2.attr("style"))) 
			return false;
		
		return true;
	}
	
	@Override
	public String toString(){
		return segments.toString();
	}

	public String getCleanText() {
		return terms.toString();
	}
	
	public List<String> getCleanTextList() {
		return terms;
	}
	
	public String getProcessedText(){
		return processedTerms;
	}
	
}
