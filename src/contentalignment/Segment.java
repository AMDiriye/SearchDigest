package contentalignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import contentminer.Utilities;

public class Segment {
	String text;
	String stopWordLessText;
	String processedTerms;
	int size;
	int uniqueTerms;
	List<String> cleanTextList;
	
	public Segment(String text)
	{
		this.text = text;
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
	
	public String toString()
	{
		return text;
	}

	public String getProcessedText() 
	{
		return  processedTerms;
	}
	
}