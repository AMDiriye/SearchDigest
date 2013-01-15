package contentminer;

import java.util.ArrayList;
import java.util.Arrays;

import org.jsoup.nodes.Comment;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class HtmlProcessor {

	static ArrayList<String> colors 
		= new ArrayList<String>(Arrays.asList("#F0F8FF", "#FAEBD7", "#00FFFF", "#0000FF", 
			"#8A2BE2", "#A52A2A", "#7FFF00", "#FF1493", "#00FF7F", "#708090", "#000000"));
	static ArrayList<Node> nodeList = new ArrayList<Node>();
	
	public static String highlightEntity(String entity){
		return null;
	}
	
	
	public static WebPage addDOMHighlighting(WebPage webPage){
		
		
		Elements style = webPage.doc.select("style");
		String cssStyle = ".highlight{padding:1px !important;} .highlight:hover{border-style:dotted !important; border-color:grey !important; border-width:1px !important;padding:0px !important;}"; 
		
		if(style.size() != 0)
			style.prepend(cssStyle);
		else {
			webPage.doc.select("head").prepend("<style>"+cssStyle+"</style");
		}
		Elements elements = webPage.doc.select("body");
		
		for(Element element : elements){
			//if(node instanceof TextNode)
			addElementHighlighting(element);
		}
		
		for(Node node : nodeList)
			addClass(node.parent());
		
		return webPage;
	}
	
	private static void addElementHighlighting(Node node){
		

		if(node.childNodes().size()== 0)
			nodeList.add(addClass(node));
		
		for(Node child : node.childNodes()){
			
			if(child.childNodes().size() > 0)
				addElementHighlighting(child);
			else{
				nodeList.add(addClass(child));
			}
				
			
		}
		
	}
	
	private static Node addClass(Node node)
	{
		if(node instanceof TextNode || node instanceof DataNode || node instanceof Comment)
		{
			return node;
		}
		
		return ((Element)node).addClass("highlight");
	}
	
}
