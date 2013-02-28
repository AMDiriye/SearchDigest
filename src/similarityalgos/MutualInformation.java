package similarityalgos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import contentalignment.Cluster;
import evaluation.Data;
import utilities.Utilities;

public class MutualInformation {

	
	public static double getValue(String segment1, String segment2){

		List<double[]> segmentDistribs = getDistributions(segment1,segment2);
		
		return JavaMI.MutualInformation.calculateMutualInformation(segmentDistribs.get(0),segmentDistribs.get(1));
	}
	
	
	public static List<double[]> getDistributions(String segment1, String segment2) {
		List<double[]> distributions = new ArrayList<double[]>();

		List<String> termsDoc = new ArrayList<String>(); 
		List<String> termsDoc2= new ArrayList<String>();
		Set<String> allTermDocs = new HashSet<String>();

		termsDoc.addAll(Arrays.asList(segment1.split(" ")));
		termsDoc2.addAll(Arrays.asList(segment2.split(" ")));

		allTermDocs.addAll(termsDoc);
		allTermDocs.addAll(termsDoc2);

		distributions.add(computeDistribution((new ArrayList<String>(allTermDocs)),segment1));
		distributions.add(computeDistribution((new ArrayList<String>(allTermDocs)),segment2));		

		return distributions;
	}


	static double[] computeDistribution(List<String> allTermDoc, String termsDoc){
		double[] distribution = new double[allTermDoc.size()];
		
		for(int i=0; i<distribution.length;i++){
			//System.out.println(allTermDoc.get(i)+" -- "+termsDoc);
			distribution[i] = Utilities.count(allTermDoc.get(i),termsDoc);
		}

		return distribution;
	}
}
