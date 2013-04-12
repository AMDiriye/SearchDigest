package document;

import java.net.URI;
import java.net.URISyntaxException;
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
	WebPageStructure webPageStructure;
	WebPageSections webPageSections;
	WebPageEntities webPageEntities;
	WebPageMedia	webPageMedia;
	
	public WebPage(Document doc){
		this.doc = doc;
		url = doc.baseUri();
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

	public void setWebPageStructure(WebPageStructure webPageStructure) {
		this.webPageStructure = webPageStructure;
		
	}
	
	public void setWebPageSegments(WebPageSections webPageSections) {
		this.webPageSections = webPageSections;
	}

	public String getTitle() {
		return title;
	}

	public String getSummary() {
		return summary;
	}

	public String getLinks() {
		return webPageStructure.toString();
	}

<<<<<<< HEAD
	public String getURL() {
		return url;
	}
	
	public WebPageStructure getWebPageStructure() {
		return webPageStructure;
	}
=======
	
>>>>>>> Edits
	
	public WebPageSections getWebPageSegments() {
		return webPageSections;
	}
	
	public String getDomainName() {
	    URI uri;
	    String domain = "";
	    
		try {
			uri = new URI(url);
			domain = uri.getHost();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	    
	    return domain.startsWith("www.") ? domain.substring(4) : domain;
	}

}
