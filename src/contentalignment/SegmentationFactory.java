package contentalignment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import contentminer.Utilities;
import contentminer.WebPage;

public class SegmentationFactory {

	List<Segment> segments;
	Elements docElements;

	int pos = 0;

	
	
	public SegmentationFactory(String url)
	{ 
		Document doc = Utilities.getDoc(url);
		docElements = doc.select("body");
		docElements.select("a").unwrap();
		docElements.select("b").unwrap();
		docElements.select("i").unwrap();
		docElements.select("br").unwrap();
		docElements.select("span").unwrap();
		docElements.select("em").unwrap();
		docElements.select("font").unwrap();

		makeSegments();
	}

	private void makeSegments()
	{
		Set<Node> nodes = new HashSet<Node>();

		for(Element node : docElements)
		{
			nodes.addAll(getTextNodes(node));
		}



		segments = new ArrayList<Segment>();

		for(Node node : nodes)
		{
			if(node instanceof Element)
			{
				segments.add(new Segment(((Element) node).text()));			
				System.out.println((((Element) node).text()));
			}
		}

		System.out.println("Done");
	}

	private List<Node> getTextNodes(Node parentNode)
	{
		List<Node> textNodes = new ArrayList<Node>();

		try {

			for(Node childNode:parentNode.childNodes())
			{
				if(childNode.childNodes().size() > 0)
				{
					List<Node> tempNodes = getTextNodes(childNode);

					for(Node tempNode : tempNodes)
					{
						if(!textNodes.contains(tempNode))
							textNodes.add(tempNode);
					}				
				}

				else
				{
					int length = getText(childNode).replaceAll("[\\s]", "").length() ;
					if(childNode instanceof TextNode  && length > 15){
						textNodes.add(childNode.parent());
					}
				}
			} 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return textNodes;
	}

	private String getText(Node node)
	{
		if(node instanceof TextNode) 
		{
			return ((TextNode)node).text();
		}
		if( node instanceof Element)
		{
			return ((Element)node).text();
		}
		return "";
	}

	public List<Segment> getSegments()
	{
		return segments;
	}
}
