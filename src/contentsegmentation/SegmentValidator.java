package contentsegmentation;

import org.jsoup.nodes.Comment;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.Element;

public class SegmentValidator {

	public static boolean splitNode(Node node){


		if(node.childNodes().size() > 0){
			if(allTextNodes(node))
				return false;

			return true;
		}


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

		if(length > 0)
			return true;

		return false;
	}


	private static boolean allTextNodes(Node _node){
		if(_node instanceof TextNode ){
			int length = removeNonAlphaNumeric(((TextNode) _node).text()).length();

			if(length > 0)
				return true;

			else return false;
		}

		for(Node childNode : _node.childNodes()){
			if(childNode.childNodes().size() > 0){
				if(!allTextNodes(childNode))
					return false;
			}
			else{
				if(childNode instanceof TextNode ){
					int length = removeNonAlphaNumeric(((TextNode) childNode).text()).length();

					if(length > 0)
						return true;
					
					else return false;
				}
			}

		}
		return false;
	}
	
	private static String removeNonAlphaNumeric(String string){
		return string.replaceAll("[^A-Za-z0-9]", "");
	}
}
