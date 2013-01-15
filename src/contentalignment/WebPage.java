package contentalignment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.jsoup.nodes.Document;

import contentminer.Utilities;

public class WebPage {

	Document doc;
	List<Segment> segments;
	
	public WebPage()
	{
		segments = new ArrayList<Segment>();
	}
	
	
	public void addSegment(Segment segment)
	{
		segments.add(segment);
	}
	
	
	public void addAllSegments(List<Segment> _segments)
	{
		segments.addAll(_segments);
	}


	
}
