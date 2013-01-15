package contentalignment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import JavaMI.MutualInformation;

public class Program {

	
public static void main(String args[]){
		
		
		System.out.println("Please enter text you want to align with");
		String text = "Publications

Books

2013

2	 	
Human-Computer Information Retrieval (Working Title) 
Ryen W. White 
Cambridge University Press - Publication date TBC 


2009 Exploratory Search: Beyond the Query-Response Paradigm  Ryen W. White and Resa A. Roth  Morgan & Claypool Series on Information Concepts, Retrieval, and Services [Morgan & Claypool website] Book Chapter 2010 Interactive Techniques  Ryen W. White  Chapter in Interactive Information Retrieval (Ian Ruthven and Diane Kelly, editors)  [Facet Publishing website] Editor";//readLine();
		Segment segment = new Segment(text);
		AlignmentEngine alignmentEngine = new AlignmentEngine(segment);
		
		System.out.println("Enter related URLs");
		String[] relatedURLs = "http://research.microsoft.com/en-us/um/people/sdumais/,http://research.microsoft.com/en-us/um/people/pauben/".split("[,]");//readLine().split("[,]");
		WebPage[] webPages = new WebPage[relatedURLs.length];
		SegmentationFactory segmentFactory;
		
		for(int i=0; i<relatedURLs.length; i++)
		{
			WebPage webPage = new WebPage();
			segmentFactory = new SegmentationFactory(relatedURLs[i]);
			webPage.addAllSegments(segmentFactory.getSegments());
			webPages[i] = webPage;
		}
		
		alignmentEngine.alignWebPages(webPages);
		
		
		for (Entry<Segment, List<Segment>> entry : alignmentEngine.alignments.entrySet()) {
		    Segment key = entry.getKey();
		    List<Segment>value = entry.getValue();

		    System.out.println("************************************");
		    System.out.println(key.text);
		    
		    for(Segment seg : value)
		    {
		    	System.out.println("--------------");
		    	System.out.println(seg.text);
		    }
		}
		
	}





	public static String readLine()
	{
		String s = "";
		try {
			InputStreamReader converter = new InputStreamReader(System.in);
			BufferedReader in = new BufferedReader(converter);
			s = in.readLine();
		} catch (Exception e) {
			System.out.println("Error! Exception: "+e); 
		}
		return s;
	}
	
	
}