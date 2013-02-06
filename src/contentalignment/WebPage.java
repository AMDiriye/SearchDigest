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
	
	
	public void addAllClusters(List<Cluster> _segments)
	{
		segments.addAll(_segments);
	}
	
	public void addAllSegments(List<Segment> _segments)
	{
		for(Segment segment : _segments){
			Cluster cluster = new Cluster();
			cluster.addSegment(segment);
			segments.add(cluster);
		}
	}
}
