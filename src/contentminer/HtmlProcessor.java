package contentminer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.nodes.Comment;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import contentalignment.Cluster;
import contentalignment.Segment;

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


	public static Document addDOMHighlighting(Document doc, List<Cluster> clusters){

		Elements style = doc.select("style");
		String cssStyle = ".item1{background:#F0F8FF} .item2{background:#FAEBD7} .item3{background:#0000FF} .item4{background:#8A2BE2} .item5{background:#A52A2A} .item6{background:#7FFF00} .item7{background:#FF1493} .item8{background:#00FF7F} .item0{background:#708090} .highlight{border-style:dotted !important; border-color:blue !important; border-width:1px !important;padding:0px !important;}"; 		

		if(style.size() != 0)
			style.prepend(cssStyle);
		else {
			doc.select("head").prepend("<style>"+cssStyle+"</style");
		}
		
		int count = 0;
		
		for(Cluster cluster : clusters){
			List<Segment> segments = cluster.getSegments();
			
			if(segments.size() != 0){
				
				for(Segment elem : segments){
					
					if(elem.getNode() instanceof Element)
						((Element)elem.getNode()).addClass("highlight item"+(count%9));
					
				}
			}
			
			count++;
		}
		
	

		for(Node node : nodeList)
			addClass(node.parent());

		return doc;
	}

}
