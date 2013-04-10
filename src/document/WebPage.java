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
		title = doc.title();
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

	public void addStructure(Elements structure) {
		this.structure = structure;
	}

	public void setWebPageStructure(WebPageStructure webPageStructure) {
		this.webPageStructure = webPageStructure;
		
	}

	public String getTitle() {
		return title;
	}

	public String getSummary() {
		return summary;
	}

	public String getLinks() {
		links
		
	}

}
