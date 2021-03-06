package document;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utilities.Utilities;

import contentalignment.Cluster;
import contentalignment.Segment;

public class WebPage{
	
	Document doc;
	String url;
	String title;
	String summary;
	String img;
	List<String> stemmedTerms;
	List<Double> stemmedTermCounts;
	Date timeOfVisit;
	boolean isHubPage;
	int numInLink;
	List<WebPageSection> segmentedWebPage;
	
	//Links
	public List<String> htmlLinks;
	public List<Element> pdfLinks;
	public List<Element> imgLinks;
	
	//Document properties
	WebPageStructure webPageStructure;
	WebPageSection webPageSections;
	WebPageEntities webPageEntities;
	WebPageMedia webPageMedia;
	
	public WebPage(Document doc){
		this.doc = doc;
		url = doc.baseUri();
		title = doc.title();
		isHubPage = false;
		numInLink = 0;
		htmlLinks = new ArrayList<String>();
		pdfLinks = new ArrayList<Element>();
		imgLinks = new ArrayList<Element>();
	}
	
	public WebPage(String content){
		isHubPage = false;
		numInLink = 0;
		htmlLinks = new ArrayList<String>();
		pdfLinks = new ArrayList<Element>();
		imgLinks = new ArrayList<Element>();
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
	
	public void setImg(String img){
		this.img = img;
	}
	
	public void addImgLink(Element imgLink){
		imgLinks.add(imgLink);
	}
	
	public void addHTMLLink(String htmlLink){
		htmlLinks.add(htmlLink);
	}
	
	public void addPDFLink(Element pdfLink){
		pdfLinks.add(pdfLink);
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
	
	public void setWebPageSegments(WebPageSection webPageSections) {
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

	
	public List<Link> getLinks() {
		return webPageStructure.getLinks();
	}

	public String getURL() {
		return url;
	}
	
	public List<String> getStemmedTerms(){
		return stemmedTerms;
	}
	
	public WebPageStructure getWebPageStructure() {
		return webPageStructure;
	}
	
	public WebPageSection getWebPageSegments() {
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

	@Override
	public String toString(){
		return title+"\n No. inLink: "+numInLink+"\n"+url+"\n"+summary;
	}

	public List<WebPageSection> getSegmentedWebPage(){
		return segmentedWebPage;
	} 
	
	public void setWebPageSegments(List<WebPageSection> segmentedWebPage) {
		this.segmentedWebPage = segmentedWebPage;	
	}

	public String getImg() {
		return img;
	}

}
