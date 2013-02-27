package similarityalgos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import contentalignment.Cluster;

public class DistributionSimilarity extends Similarity{


	public static List<double[]> getDistributions(Cluster segment, Cluster segment2) {
		List<double[]> distributions = new ArrayList<double[]>();
		
		List<String> termsDoc = new ArrayList<String>(); 
		List<String> termsDoc2= new ArrayList<String>();
		Set<String> allTermDocs = new HashSet<String>();
		
		termsDoc.addAll(segment.getCleanTextList());
		termsDoc2.addAll(segment2.getCleanTextList());
		
		allTermDocs.addAll(termsDoc);
		allTermDocs.addAll(termsDoc2);
		
		distributions.add(computeDistribution((new ArrayList<String>(allTermDocs)),segment));
		distributions.add(computeDistribution((new ArrayList<String>(allTermDocs)),segment2));		
		
		return distributions;
	}
	
	
	private static double[] computeDistribution(List<String> termDoc, Cluster segment)
	{
		double[] distribution = new double[termDoc.size()];
		String segmentText = segment.getProcessedText();
		
		for(int i=0; i<distribution.length;i++){
			distribution[i] = count(termDoc.get(i),segmentText);
		}
		
		return distribution;
	}

}
