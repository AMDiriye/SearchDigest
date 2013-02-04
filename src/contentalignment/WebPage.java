package contentalignment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.jsoup.nodes.Document;

import contentminer.Utilities;

public class WebPage {

	Document doc;
	List<Cluster> segments;
	
	public WebPage(Document doc)
	{
		this.doc = doc;
		segments = new ArrayList<Cluster>();
	}
	
	
	public void addSegment(Cluster segment)
	{
		segments.add(segment);
	}
	
	
	public void addAllSegments(List<Cluster> _segments)
	{
		segments.addAll(_segments);
	}
	
}
