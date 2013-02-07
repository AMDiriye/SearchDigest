package contentalignment;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class EntityExtractor {


	List<Segment> webPageSegments;
	List<Node> webPageHeadings;
	List<Cluster> clusters;

	public EntityExtractor(List<Segment> webPageSegments, Document doc){
		this.webPageSegments = webPageSegments;
		this.webPageHeadings = new ArrayList<Node>();
		this.clusters = new ArrayList<Cluster>();
		findHeadings(doc);
		segmentContent();
	}



	private void findHeadings(Document doc) {
		Elements heading = doc.select("h1, h2, h3, h4, h5, h6");

		for(Node node : heading){
			webPageHeadings.add(node);
		}

	}



	/** Take webpage content and do the following:
	 *   1)identfy wheer soft segments are
	 *   2)compute  score based on distribution of terms and similarity
	 *   3)move segments ariynd
	 */
	private void segmentContent(){

		Cluster cluster = new Cluster();
		boolean isSameCluster;

		for(int i=0; i < webPageSegments.size(); i++){

			isSameCluster = cluster.isSameCluster(webPageSegments.get(i));

			if(!isSameCluster){

				if(cluster.segments.size() != 0)
					clusters.add(cluster);

				cluster = new Cluster();
			}

			cluster.addSegment(webPageSegments.get(i));
		}

		//last cluster
		if(cluster != null){
			clusters.add(cluster);

		}
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

	public List<Cluster> getCluster(){
		return clusters;
	}

}