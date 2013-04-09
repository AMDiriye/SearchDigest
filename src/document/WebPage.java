package document;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import contentalignment.Cluster;
import contentalignment.Segment;

public class WebPage{
	
	Document doc;
	String url;
	String title;
	String summary;
	
	//Document properties
	Elements links;
	Elements media;
	Elements structure;
	
	WebPageStructure webPageStructure;
	WebPageSections webPageSections;
	WebPageEntities webPageEntities;
	
	public WebPage(Document doc){
		this.doc = doc;
	}
	
	public void setSummary(String summary){
		this.summary = summary;
	}
	
	public String getContent() {
		return doc.text();
	}

	public Document getDoc() {
		return doc;
	}

	public void setLinks(Elements links) {
		this.links = links;
	}

	public void setMedia(Elements media) {
		this.media = media;	
	}

	public void addAllClusters(List<Cluster> _segments){
		Elements segments;
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
	
	/*for (Element src : media) {
		if (src.tagName().equals("img"))
			print(" * %s: <%s> %sx%s (%s)",
					src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
					trim(src.attr("alt"), 20));
	}


	print("\nLinks: (%d)", links.size());
	for (Element link : links) {
		print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
	}*/

	private String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width-1) + ".";
		else
			return s;
	}

	public void addStructure(Elements structure) {
		this.structure = structure;
	}
}
