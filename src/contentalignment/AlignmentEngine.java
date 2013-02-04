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
	Map<Cluster, List<Cluster>> alignments;
	
	
	public AlignmentEngine(WebPage webpage){
		this.webpage = webpage;
		alignments = new HashMap<Cluster, List<Cluster>>();
	}
	
	
	public AlignmentEngine(Cluster segment){
		webpage = new WebPage(null);
		webpage.addSegment(segment);
		alignments = new HashMap<Cluster,  List<Cluster>>();
	}
	
	
	public void alignWebPages(WebPage[] webPages){
		
		
		for(int i=0; i<webPages.length;i++)
		{
			List<Cluster> tempPageSegments = webPages[i].segments;
			
			//need to create new idf for each segment
			InverseSegmentFreq isf;
			isf = new InverseSegmentFreq(tempPageSegments);
			Utilities.isf = isf;
			
			
			
			for(int j=0; j < webpage.segments.size(); j++){
				
				double highestMIScore = Double.NEGATIVE_INFINITY;
				Cluster bestSegment = null;
				
				for(int k=0; k < tempPageSegments.size(); k++)
				{

					List<double[]> segmentDistribs = Utilities.getDistributions(tempPageSegments.get(k),webpage.segments.get(j));
					
					double tempMIScore = Utilities.cosineSimilarity(tempPageSegments.get(k).getCleanTextList(),webpage.segments.get(j).getCleanTextList());//
					//MutualInformation.calculateMutualInformation(segmentDistribs.get(0),segmentDistribs.get(1));
							//Utilities.klDivergence(segmentDistribs.get(0),segmentDistribs.get(1));
				
					if(tempMIScore > highestMIScore)
					{
						highestMIScore = tempMIScore;
						bestSegment = tempPageSegments.get(k);
					}
				}
				//System.out.println("score: "+highestMIScore );
				tempPageSegments.remove(bestSegment);
				addAlignment(webpage.segments.get(j),bestSegment);				
			}
		}
	}
	
	private void addAlignment(Cluster segment, Cluster bestSegment) {
		if(alignments.containsKey(segment))
		{
			List<Cluster> segments = alignments.get(segment);
			segments.add(bestSegment);
			alignments.put(segment, segments);
		}
		else
		{
			List<Cluster> segments = new ArrayList<Cluster>();
			segments.add(bestSegment);
			alignments.put(segment, segments);
		}
	}


	public List<Cluster> getAlignedSegments(Segment segment)
	{
		return alignments.get(segment);
	}
	
	
	public Map<Cluster, List<Cluster>> getAlignments(){
		return alignments;
	}

}
