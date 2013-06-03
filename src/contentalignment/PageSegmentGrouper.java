package contentalignment;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import similarityalgos.CharacterBasedDistribution;

import document.WebPageSection;

public class PageSegmentGrouper {


	List<Segment> webPageSegments;
	List<Node> webPageHeadings;
	List<WebPageSection> clusters;

	public PageSegmentGrouper(List<Segment> webPageSegments, Document doc){
		this.webPageSegments = webPageSegments;
		this.webPageHeadings = new ArrayList<Node>();
		this.clusters = new ArrayList<WebPageSection>();
		findHeadings(doc);
		groupSegments(doc);
	}

	private void findHeadings(Document doc) {
		Elements heading = doc.select("h1, h2, h3, h4, h5, h6");

		for(Node node : heading){
			webPageHeadings.add(node);
		}

	}

	/** Take webpage content and do the following:
	 *   1)identify where soft segments are
	 *   2)compute  score based on distribution of terms and similarity
	 *   3)move segments around
	 */
	private void groupSegments(Document doc){

		Cluster cluster = new Cluster();
		boolean isSameCluster;
		int textSize = doc.body().text().split(" ").length;
		int count = 0;

		for(int i=0; i < webPageSegments.size(); i++){

			isSameCluster = cluster.isSameCluster(webPageSegments.get(i));

			if(!isSameCluster){

				if(cluster.segments.size() != 0){
					count += cluster.getSegmentText().split(" ").length;
					double position = count/((double)textSize);
					if(cluster.getText().trim().length() > 100){
						addNewWebPageSection(cluster, position);
					}
				}
				cluster = new Cluster();
			}
			cluster.addSegment(webPageSegments.get(i));
		}

		//last cluster
		if(cluster != null){
			count += cluster.getSegmentText().split(" ").length;
			double position = count/((double)textSize);
			if(cluster.getText().trim().length() > 100){
				addNewWebPageSection(cluster, position);
			}
		}
	}

	private void addNewWebPageSection(Cluster cluster, double pageLocation) {
		WebPageSection webPageSection = new WebPageSection();
		webPageSection.addCluster(cluster);

		//Need to add segment name
		webPageSection.setSectionSize(cluster.getSegmentText().split(" ").length);

		CharacterBasedDistribution distribution = new CharacterBasedDistribution(cluster.getSegmentText());
		webPageSection.setDistrbution(distribution.distribution);

		webPageSection.setPageLocation(pageLocation);
		clusters.add(webPageSection);
	}

	//Pulls out headings out of content based on <h> tags and CSS styling
	public List<String> extractHeadings(){
		return null;
	}


	public List<Segment> getSegments(){
		return null;
	}

	public List<String> getLabels(){
		return null;
	}

	public List<WebPageSection> getCluster(){
		return clusters;
	}

}