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
	int state = 0;
	
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

		if(segments.size() < 1)
			return true;

		else if(!isSameType((Element)segment.node))
			return false;

		return true;
	}


	private boolean isSameType(Element elem1){

		if(state == 0){
			
			Element elem2 = (Element) segments.get(0).node;
			
			if(!isHeading((elem2).tagName())){
				
				if(isHeading((elem1).tagName())){
					return false;
				}				
				state = 1;
				return true;
			
			}
			else if(isHeading((elem1).tagName())){
				return true;
			
			}
			else {
				state = 1;
				return true;
			}
		}
		
		if(state == 1){
			
				int pos = segments.size() > 1?segments.size() -1:1;

				Element elem2 = (Element) segments.get(pos).node;
				
				if(elem1.attr("style").equalsIgnoreCase(elem2.attr("style")) && elem1.className().equalsIgnoreCase(elem2.className()) ){ 

					System.out.println(elem1.attr("style"));
					return true;
				}
				
				else{
					state = 0;
					return false;
				}
			
		}
		
		return false;
	}


	private boolean isHeading(String tagName){
		if(tagName.equalsIgnoreCase("h1") ||tagName.equalsIgnoreCase("h2")||tagName.equalsIgnoreCase("h3")||tagName.equalsIgnoreCase("h4")
				|| tagName.equalsIgnoreCase("h5")||tagName.equalsIgnoreCase("h6"))
			return true;
		else return false;
	}

	
	private boolean containsTextNode(Node _node){
		
		if(_node instanceof TextNode ){
			int length = ((TextNode) _node).text().replaceAll("[^A-Za-z0-9]", "").length();
			
			if(length > 0)
				return true;
			
			else return false;
		}
		
		for(Node childNode : _node.childNodes()){
			if(childNode.childNodes().size() > 0){
				if(containsTextNode(childNode))
					return true;
			}
			else{
				if(childNode instanceof TextNode ){
					int length = ((TextNode) childNode).text().replaceAll("[^A-Za-z0-9]", "").length();
					
					if(length > 0)
						return true;

				}
			}
			
		}
		return false;
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