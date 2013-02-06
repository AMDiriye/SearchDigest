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
		
		args = "http://www.amazon.com/Nikon-COOLPIX-Digital-Camera-NIKKOR/dp/B0073HSJV0/ref=sr_1_13?ie=UTF8&qid=1359990405&sr=8-13&keywords=camera,http://www.amazon.com/GE-X500-BK-Optical-Digital-Camera/dp/B004LB4SAM/ref=sr_1_15?ie=UTF8&qid=1359990405&sr=8-15&keywords=camera".split("[,]");
		
		SegmentationFactory segmentFactory = new SegmentationFactory(args[0]);
		EntityExtractor entityExtractor = new EntityExtractor(segmentFactory.getSegments(), segmentFactory.getDoc());
		
		List<Cluster> clusters = entityExtractor.getCluster();
		
		for(Cluster cluster : clusters){
			System.out.println(cluster.toString());
			System.out.println();
		}
		
	}

}
