import java.util.List;

import contentalignment.AlignmentEngine;
import contentalignment.Cluster;
import contentalignment.EntityExtractor;
import contentalignment.Segment;
import contentalignment.SegmentationFactory;
import contentalignment.WebPage;


public class SegmentPage {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SegmentationFactory segmentFactory = new SegmentationFactory(args[0]);
		EntityExtractor entityExtractor = new EntityExtractor(segmentFactory.getSegments(), segmentFactory.getDoc());
		
		List<Cluster> clusters = entityExtractor.getCluster();
		
		for(Cluster cluster : clusters){
			System.out.println(cluster.toString());
			System.out.println();
		}
		
	}

}
