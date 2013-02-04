package contentalignment;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class SegmentExtractor {

	List<Node> webPageHeadings;
	List<Segment> segments;
	
	public SegmentExtractor(){
		webPageHeadings = new ArrayList<Node>();
		segments = new ArrayList<Segment>();
	}
	
	public List<Segment> extractSegments(Document doc){
		
		findHeadings(doc);
		Elements docElements = doc.select("body");
		docElements.select("a").unwrap();
		
		for(Element element : docElements){
			getSegments(element);
		}
		return null;
	}
	
	public List<Segment> getSegments(Node node){
		
		List<Segment> tempNodeSegments = null;
		
		if(splitNode(node) == true){
			tempNodeSegments = getSegments(node);
			
		}
			
		else{
			
		}
		return tempNodeSegments;
	}


	public boolean splitNode(Node node){
		if(node.childNodes().size() == 0)
			return false;
		
		if(node instanceof TextNode)
			return false;
		
		if(isChildrenTextNodes(node))
			return false;
		else
			return true;
	}

	private boolean isChildrenTextNodes(Node node){
		
		for(Node childNode : node.childNodes()){
			if(!(childNode instanceof TextNode))
				return false;
		}
		
		return true;
	}
	
	private void findHeadings(Document doc) {
		Elements heading = doc.select("h1, h2, h3, h4, h5, h6");
		
		for(Node node : heading){
			webPageHeadings.add(node);
		}
		
	}
	
}
