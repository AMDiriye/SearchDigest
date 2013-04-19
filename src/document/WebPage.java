package document;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import utilities.Utilities;

import contentalignment.Cluster;
import contentalignment.Segment;

public class WebPage{
	
	Document doc;
	String url;
	String title;
	String summary;
	List<String> stemmedTerms;
	List<Double> stemmedTermCounts;
	Date timeOfVisit;
	boolean isHubPage;
	int numInLink;
	
	//Document properties
	WebPageStructure webPageStructure;
	WebPageSections webPageSections;
	WebPageEntities webPageEntities;
	WebPageMedia webPageMedia;
	
	public WebPage(Document doc){
		this.doc = doc;
		url = doc.baseUri();
		title = doc.title();
		isHubPage = false;
		numInLink = 0;
	}
	
	public void setStemmedTerms(List<String> stemmedTerms){
		this.stemmedTerms = stemmedTerms;
	}
	
	public void setStemmedTermCounts(List<Double> stemmedTermCounts){
		this.stemmedTermCounts = stemmedTermCounts;
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

	public Double getTermCount(String term){
		int index = stemmedTerms.indexOf(term);
		
		return (index == -1) ?new Double(0) : stemmedTermCounts.get(index);
		
	}	
	
	public void setInLinks(int numInLink){
		this.numInLink = numInLink;
	}
	
	public void setWebPageStructure(WebPageStructure webPageStructure) {
		this.webPageStructure = webPageStructure;
	}
	
	public void incrementNumInLink() {
		numInLink++;
	}
	
	public void setWebPageSegments(WebPageSections webPageSections) {
		this.webPageSections = webPageSections;
	}
	
	public void setIsHubPage(boolean isHubPage){
		this.isHubPage = isHubPage;
	}
	
	public boolean getIsHubPage(){
		return isHubPage;
	}

	public String getTitle() {
		return title;
	}

	public String getSummary() {
		return summary;
	}
	
	public int getNumInLink() {
		return numInLink;
	}

	
	public String getLinks() {
		return webPageStructure.toString();
	}

	public String getURL() {
		return url;
	}
	
	public WebPageStructure getWebPageStructure() {
		return webPageStructure;
	}
	
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
