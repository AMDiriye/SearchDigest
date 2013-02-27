package similarityalgos;

import java.util.List;

import contentalignment.Cluster;
import utilities.Utilities;

public class MutualInformation {

	
	public static double getValue(Cluster segment, Cluster segment2){

		List<double[]> segmentDistribs = Utilities.getDistributions(segment,segment2);
		
		return JavaMI.MutualInformation.calculateMutualInformation(segmentDistribs.get(0),segmentDistribs.get(1));
	}
}
