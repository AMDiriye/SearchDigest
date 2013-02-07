package contentalignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import contentminer.EntityFactory;
import contentminer.NamedEntity;
import contentminer.Utilities;

public class Segment {

	String text;
	String stopWordLessText;
	String processedTerms;
	int size;
	int uniqueTerms;
	List<String> cleanTextList;
	Node node;

	public Segment(String text, Node node)
	{
		this.text = text;
		this.node = node;
		stopWordLessText = Utilities.removeStopWords(text);
		size = stopWordLessText.split("[\\s]").length;

	    processedTerms = Utilities.stem(stopWordLessText);
		cleanTextList = new ArrayList<String>();
		cleanTextList.addAll(Arrays.asList(processedTerms.split("[\\s]")));

		HashSet set = new HashSet();
		set.add(stopWordLessText);
		uniqueTerms = set.size();
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



	@Override
	public String toString(){
		return ((Element) node).text();
	}
}