package run;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import contentalignment.AlignmentEngine;
import contentalignment.Cluster;
import contentalignment.EntityExtractor;
import contentalignment.Segment;
import contentalignment.SegmentationFactory;
import contentalignment.WebPage;


public class AlignPages {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		args = "http://research.microsoft.com/en-us/um/people/sdumais/,http://research.microsoft.com/en-us/um/people/pauben/".split("[,]");
		WebPage[] webPages = new WebPage[args.length];
		SegmentationFactory segmentFactory = null;
		AlignmentEngine alignmentEngine;
		EntityExtractor entityExtractor;

		for(int i=0; i<args.length; i++)
		{
			segmentFactory = new SegmentationFactory(args[i]);
			//entityExtractor = new EntityExtractor(segmentFactory.getSegments(), segmentFactory.getDoc());

			WebPage webPage = new WebPage((segmentFactory.getDoc()));
			webPage.addAllSegments(segmentFactory.getSegments());
			//webPage.addAllClusters(entityExtractor.getCluster());
			webPages[i] = webPage;
		}

		alignmentEngine = new AlignmentEngine(webPages[0]);
		alignmentEngine.alignWebPages(Arrays.copyOfRange(webPages, 1, webPages.length));
		Map<Cluster, List<Cluster>> alignments = alignmentEngine.getAlignments();

		for (Entry<Cluster, List<Cluster>> entry : alignments.entrySet()) {
			Cluster key = entry.getKey();
			List<Cluster> value = entry.getValue();

			if(value.size() != 0){
				System.out.println("************************************");
			System.out.println(key.toString());

			for(Cluster seg : value)
			{
				if(seg != null)
					System.out.println(seg.toString());

			}
			}
		}


	}

}
