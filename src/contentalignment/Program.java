package contentalignment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import contentminer.InverseSegmentFreq;
import contentminer.Utilities;

import JavaMI.MutualInformation;

public class Program {

	
public static void main(String args[]){
		
		
		System.out.println("Please enter text you want to align with");
		String text = "I am a Senior Researcher at Microsoft Research, Redmond. I am a member of the CLUES group.";//readLine();
		Segment segment = new Segment(text, null);
		AlignmentEngine alignmentEngine = new AlignmentEngine(segment);
		
		System.out.println("Enter related URLs");
		String[] relatedURLs = "http://research.microsoft.com/en-us/um/people/sdumais/,http://research.microsoft.com/en-us/um/people/pauben/".split("[,]");//readLine().split("[,]");
		WebPage[] webPages = new WebPage[relatedURLs.length];
		SegmentationFactory segmentFactory = null;
		
		
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
