package contentsegmentation;

import org.jsoup.nodes.Comment;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.Element;

public class SegmentValidator {

	public static boolean splitNode(Node node){
		
		
		if(node.childNodes().size() > 0)
			return true;
		
		if(node instanceof TextNode)
			return false;
		
		if(node instanceof Comment || node instanceof DataNode)
			return false;
		
		if(((Element) node).tagName() == "p")
			return false;
		
		return true;
	}

	public static boolean validText(Node childNode, String text) {
		
		int length = text.replaceAll("[^A-Za-z0-9]", "").length();
		
		if(childNode instanceof TextNode  && length > 0)
			return true;
		
		return false;
	}
	

}
