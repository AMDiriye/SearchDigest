package contentalignment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.XmlDeclaration;
import org.jsoup.select.Elements;

import utilities.Utilities;
import contentsegmentation.SegmentValidator;
import document.EntityBasedWebPage;

public class SegmentationFactory {

	List<Segment> segments;
	Elements docElements;
	Document doc;
	int pos = 0;
	
	
	
	public SegmentationFactory(String url)
	{ 
		doc = Utilities.getDoc(url);
		Elements _elements = doc.select("h1,h2,h3,h4,h5,h6");
		_elements.size();
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
		List<Node> nodes = new ArrayList<Node>();

		for(Element node : docElements)
		{

				nodes.addAll(getTextNodes(node));

		}



		segments = new ArrayList<Segment>();

		for(Node node : nodes)
		{
			//if(node instanceof Element)
		//	{
				segments.add(new Segment(getText(node), node));			
				//System.out.println((((Element) node).text()));
		//	}
		}

		//System.out.println("Done");
		//System.out.println("***************************");
	}

	private List<Node> getTextNodes(Node parentNode)
	{
		if(!Utilities.isValidNode(parentNode))
			return null;
		
		List<Node> textNodes = new ArrayList<Node>();

		if(!SegmentValidator.splitNode(parentNode))
		{
			textNodes.add(parentNode);
			
			return textNodes;		
		}


		else{
			try {
		

			for(Node childNode:parentNode.childNodes())
			{
				
				if(!Utilities.isValidNode(childNode))
					continue;
				
				if(SegmentValidator.splitNode(childNode))
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
					
					if(SegmentValidator.validText(childNode, getText(childNode))){
						textNodes.add(childNode);
					}
				}
			}
		}
		catch(Exception e)
		{
			 System.err.println(e.getStackTrace());
		}

		return textNodes;
		}
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

	public Document getDoc() {
		return doc;
	}
	
}
