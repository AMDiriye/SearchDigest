package document;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;

import contentalignment.Cluster;
import contentalignment.Segment;

public class WebPageSections {

	public List<Cluster> segments;
	
	public WebPageSections(){
		segments = new ArrayList<Cluster>();
	}
	
	
	public void setSegment(Cluster segment){
		segments.add(segment);
	}
	
	
	public void addAllClusters(List<Cluster> _segments){
		segments.addAll(_segments);
	}
	
	
	public void addAllSegments(List<Segment> _segments){
		
		for(Segment segment : _segments){
			Cluster cluster = new Cluster();
			cluster.addSegment(segment);
			segments.add(cluster);
		}
	}
	
	
	public List<Cluster> getSegments(){
		return segments;
	}
	
}
