package contentalignment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import contentminer.InverseSegmentFreq;
import contentminer.Utilities;

import JavaMI.MutualInformation;


   

public class AlignmentEngine { 

	WebPage webpage;
	Map<Segment, List<Segment>> alignments;
	
	
	public AlignmentEngine(WebPage webpage){
		this.webpage = webpage;
		alignments = new HashMap<Segment, List<Segment>>();
	}
	
	
	public AlignmentEngine(Segment segment){
		webpage = new WebPage();
		webpage.addSegment(segment);
		alignments = new HashMap<Segment,  List<Segment>>();
	}
	
	
	public void alignWebPages(WebPage[] webPages){
		
		
		for(int i=0; i<webPages.length;i++)
		{
			List<Segment> tempPageSegments = webPages[i].segments;
			
			//need to create new idf for each segment
			InverseSegmentFreq isf;
			isf = new InverseSegmentFreq(tempPageSegments);
			Utilities.isf = isf;
			
			
			
			for(int j=0; j < webpage.segments.size(); j++){
				
				double highestMIScore = Double.NEGATIVE_INFINITY;
				Segment bestSegment = null;
				
				for(int k=0; k < tempPageSegments.size(); k++)
				{

					List<double[]> segmentDistribs = Utilities.getDistributions(tempPageSegments.get(k),webpage.segments.get(j));
					
					double tempMIScore = Utilities.cosineSimilarity(tempPageSegments.get(k).cleanTextList,webpage.segments.get(j).cleanTextList);//
					//MutualInformation.calculateMutualInformation(segmentDistribs.get(0),segmentDistribs.get(1));
							//Utilities.klDivergence(segmentDistribs.get(0),segmentDistribs.get(1));
				
					if(tempMIScore > highestMIScore)
					{
						highestMIScore = tempMIScore;
						bestSegment = tempPageSegments.get(k);
					}
				}
				System.out.println("score: "+highestMIScore );
				tempPageSegments.remove(bestSegment);
				addAlignment(webpage.segments.get(j),bestSegment);				
			}
		}
	}
	
	private void addAlignment(Segment segment, Segment bestSegment) {
		if(alignments.containsKey(segment))
		{
			List<Segment> segments = alignments.get(segment);
			segments.add(bestSegment);
			alignments.put(segment, segments);
		}
		else
		{
			List<Segment> segments = new ArrayList<Segment>();
			segments.add(bestSegment);
			alignments.put(segment, segments);
		}
	}


	public List<Segment> getAlignedSegments(Segment segment)
	{
		return alignments.get(segment);
	}
	

}
