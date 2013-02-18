package contentsegmentation;

import org.jsoup.nodes.Node;

public class WebPageElement {

	
	private boolean isSegment = false;
	private Node node;
	
	
	public WebPageElement(Node node){
		this.node = node;
	}
	
	public void setIsSegment(boolean isSegment){
		this.isSegment = isSegment;
	}
	
	
}
