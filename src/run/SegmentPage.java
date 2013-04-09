package run;
import java.util.List;

import org.jsoup.nodes.Document;

import utilities.DataWriter;
import utilities.HtmlProcessor;
import utilities.Utilities;

import contentalignment.AlignmentEngine;
import contentalignment.Cluster;
import contentalignment.EntityExtractor;
import contentalignment.Segment;
import contentalignment.SegmentationFactory;
import document.SegmentBasedWebPage;


public class SegmentPage {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SegmentationFactory segmentFactory = new SegmentationFactory("http://athletics.cmu.edu/sports/msoc/2012-13/roster");
		EntityExtractor entityExtractor = new EntityExtractor(segmentFactory.getSegments(), segmentFactory.getDoc());
		
		List<Cluster> clusters = entityExtractor.getCluster();
		Document doc = HtmlProcessor.addDOMHighlighting(segmentFactory.getDoc(), clusters);
		
		DataWriter.writeFile("../html/segmentedPage.html", doc.toString());
		Utilities.openFileInBrowser("html/segmentedPage.html");
		
		for(Cluster cluster : clusters){
			System.out.println(cluster.toString());
			System.out.println();
		}
		
	}

}
