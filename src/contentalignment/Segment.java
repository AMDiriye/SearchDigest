package contentalignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import namedentities.EntityFactory;
import namedentities.NamedEntity;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import utilities.Utilities;


public class Segment {

	String text;
	String stopWordLessText;
	String processedTerms;
	int size;
	int uniqueTerms;
	List<String> cleanTextList;
	Node node;
	String label;
	
	public Segment(String text, Node node){
		this.text = text;
		this.node = node;
		stopWordLessText = Utilities.removeStopWords(text.replaceAll("[\\.\\/-]", " ")).trim();
		size = stopWordLessText.split("[\\s]").length;

	    processedTerms = Utilities.stem(stopWordLessText);
		cleanTextList = new ArrayList<String>();
		cleanTextList.addAll(Arrays.asList(processedTerms.split(" ")));

		HashSet set = new HashSet();
		set.add(stopWordLessText);
		uniqueTerms = set.size();
	}

	
	public Segment(String text){
		this(text, null);
	}
	
	public String getText()
	{
		return text;
	}

	public List<String> getCleanTextList()
	{
		return cleanTextList;
	}


	public String getProcessedText() 
	{
		return  processedTerms;
	}

	public Node getNode(){
		return node;
	}

	public void addLabel(String label){
		this.label = label;
	}

	@Override
	public String toString(){
		if(node == null)
			return text+" ";
		else if(node instanceof Element)
			return ((Element) node).text()+" ";
		else return node.toString()+" ";

	}
}