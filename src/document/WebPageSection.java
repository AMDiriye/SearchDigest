package document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.nodes.Document;

import contentalignment.Cluster;
import contentalignment.Segment;

public class WebPageSection{

	public List<Cluster> segments;
	String segmentName;
	double pageLocation;
	int size;
	double[] characterDistribution;
	String text;
	boolean isAligned;
	
	public WebPageSection(){
		segments = new ArrayList<Cluster>();
		text = "";
	}
	
	public void setDistrbution(double[] characterDistribution){
		this.characterDistribution = characterDistribution;
	}
	
	public void setPageLocation(double pageLocation){
		this.pageLocation = pageLocation;
	}
	
	public void setSectionSize(int size){
		this.size = size;
	}
	
	public void setSegmentName(String segmentName){
		this.segmentName = segmentName;
	}
	
	public void setSegment(Cluster segment){
		segments.add(segment);
		text += segment.getText()+" ";
	}
	
	public boolean isAligned(){
		return isAligned;
	}
	
	public void addCluster(Cluster segment){
		this.segments.add(segment);
		text += segment.getText()+" ";
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
	
	public double getRelativePageLocation(){
		return pageLocation;
	}
	
	public List<Segment> getAllSegments(){
		List<Segment> allSegments = new ArrayList<Segment>();
		
		for(Cluster segment : segments){
			allSegments.addAll(segment.getSegments());
		}	
		return allSegments;
	}
	

	public List<String> getCleanTextList(){
		List<String> segmentText = new ArrayList<String>();
		
		for(Cluster segment : segments){			
			segmentText.addAll(segment.getCleanTextList());
		}
		return segmentText;
	}
	
	public String getProcessedText(){
		String processedText = "";
		
		for(Cluster segment : segments){			
			processedText += segment.getCleanText()+" ";
		}
		return processedText;
	}
	
	public String getText(){
		String segmentText = "";
		
		for(Cluster segment : segments){
			segmentText += Arrays.toString(segment.getCleanTextList().toArray(new String[]{}))+" ";
		}
		
		return segmentText;
	}

	public void aligned() {
		isAligned = true;	
	}
	
	@Override
	public String toString(){
		return text;
	}
	
}
