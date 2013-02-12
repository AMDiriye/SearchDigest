import java.util.List;

import org.jsoup.nodes.Document;

import contentalignment.AlignmentEngine;
import contentalignment.Cluster;
import contentalignment.EntityExtractor;
import contentalignment.Segment;
import contentalignment.SegmentationFactory;
import contentalignment.WebPage;
import contentminer.DataWriter;
import contentminer.HtmlProcessor;
import contentminer.Utilities;


public class SegmentPage {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SegmentationFactory segmentFactory = new SegmentationFactory("http://research.microsoft.com/en-us/um/people/ryenw/index.html");
		EntityExtractor entityExtractor = new EntityExtractor(segmentFactory.getSegments(), segmentFactory.getDoc());
		
		List<Cluster> clusters = entityExtractor.getCluster();
		Document doc = HtmlProcessor.addDOMHighlighting(segmentFactory.getDoc(), clusters);
		
		DataWriter.writeFile("../html/jhuang.html", doc.toString());
		Utilities.openFileInBrowser("html/jhuang.html");
		
		for(Cluster cluster : clusters){
			System.out.println(cluster.toString());
			System.out.println();
		}
		
	}

}
