package contentalignment;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;

public class EntityExtractor {

	
	List<Segment> webPageSegments;
	List<String> webPageHeadings;
	List<Node> sectionHeadings;
	
	public EntityExtractor(List<Segment> webPageSegments, Document doc){
		this.webPageSegments = webPageSegments;
		this.webPageHeadings = new ArrayList<String>();

		findHeadings(doc);
	}
	 
	
	
	private void findHeadings(Document doc) {
		
		
	}



	/** Take webpage content and do the following:
	*   1)identfy wheer soft segments are
	*   2)compute  score based on distribution of terms and similarity
	*   3)move segments ariynd
	*/
	private void segmentContent(){
		
		Cluster cluster = null;
		List<Cluster> clusters = new ArrayList<Cluster>();
		
		for(int i=0; i < webPageSegments.size(); i++){

			if(sectionHeadings.contains(webPageSegments.get(i))){
				
				 if(cluster != null)
				 {
					 clusters.add(cluster);
				 }
				 
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
	
}
