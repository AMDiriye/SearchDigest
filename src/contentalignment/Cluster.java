package contentalignment;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

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

		if(segments.size() < 3){
			return true;
		}
		else{// if(segments.size() == 3){

			if(!isSameType((Element)segment.node,(Element) segments.get(1).node))
				return false;

			return true;

		}
		/*else{
			if(segment.node instanceof Element)
			{
				//get the first element and see of they are the same
				if(isSameType((Element) segment.node,(Element) segments.get(0).node))
					return true;

				else return false;
			}
			else{
				return true;
			}
		}*/
	}


	private boolean isSameType(Element elem1, Element elem2){

		if( ((Node) elem1) instanceof TextNode && !isHeading((elem1).tagName())){
			return true;
		}

		if(elem1.tagName() != elem2.tagName())
			return false;

		else if(!elem1.attr("style").equalsIgnoreCase(elem2.attr("style"))) 
			return false;

		return true;
	}


	private boolean isHeading(String tagName){
		if(tagName.equalsIgnoreCase("h1") ||tagName.equalsIgnoreCase("h2")||tagName.equalsIgnoreCase("h3")||tagName.equalsIgnoreCase("h4")
				|| tagName.equalsIgnoreCase("h5")||tagName.equalsIgnoreCase("h6"))
			return false;
		else return true;
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

	public List<Segment> getSegments(){
		return segments;
	}

}