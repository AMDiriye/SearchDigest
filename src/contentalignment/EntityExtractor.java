package contentalignment;

import java.util.ArrayList;
import java.util.List;

public class EntityExtractor {

	
	List<Segment> webPageSegments;
	List<String> webPageHeadings;
	
	public EntityExtractor(List<Segment> webPageSegments){
		this.webPageSegments = webPageSegments;
		this.webPageHeadings = new ArrayList<String>();
	}
	 
	
	
	/** Take webpage content and do the following:
	*   1)identfy wheer soft segments are
	*   2)compute  score based on distribution of terms and similarity
	*   3)move segments ariynd
	*/
	private void segmentContent(){
		
		
		
		return;
		
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
