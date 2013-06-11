package websummary;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import contentalignment.Cluster;
import contentalignment.SegmentationFactory;

import utilities.Utilities;

import net.sf.classifier4J.summariser.SimpleSummariser;
import document.Link;
import document.WebPage;
import document.WebPageSection;


import document.WebPageStructure;

public class WebSummaryFactory {


	public WebPage addWebPageProperties(WebPage webPage){
		webPage = addWebPageSummarization(webPage);
		webPage = addWebPageLinks(webPage);
		webPage = addWebPageMedia(webPage);
		webPage = addWebPageStructure(webPage);
		webPage = addWebPageSegmentation(webPage);
		return webPage;
	}

	private WebPage addWebPageSummarization(WebPage webpage){
		SimpleSummariser summariser = new SimpleSummariser();
		//System.out.println("page URL:"+webpage.getURL()+"\n content:"+webpage.getContent());

		if(webpage.getContent().length() > 0){
			String summary = summariser.summarise(webpage.getContent(), 1);
			webpage.setSummary(summary);
		}
		return webpage;
	}

	private WebPage addWebPageLinks(WebPage webpage){

		Document doc = webpage.getDoc();

		Elements links = doc.select("a[href]");
		Elements subHeadings = webpage.getDoc().select("h1,h2,h3,h4,h5,h6");

		WebPageStructure webPageStructure = new WebPageStructure();

		for (Element link : links) {
			String text ="";
			if(link.childNodes().size()>0){
				text = link.childNode(0).toString();
			}
			webPageStructure.addLink(link.attr("abs:href"), text);
		}

		for (Element subHeading : subHeadings) {
			webPageStructure.addSubHeadings(subHeading.text());
		}

		webpage.setWebPageStructure(webPageStructure);

		return webpage;
	}

	private WebPage addWebPageMedia(WebPage webpage){

		Document doc = webpage.getDoc();
		Elements media = doc.select("[src]");
		//	webpage.setMedia(media);
		/*for (Element src : media) {
		if (src.tagName().equals("img"))
			print(" * %s: <%s> %sx%s (%s)",
					src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
					trim(src.attr("alt"), 20));
		}	
		 */
		return webpage;
	}

	//TODO: Need to add way of extracting structure based on segments
	private  WebPage addWebPageStructure(WebPage webpage){
		return webpage;
	}

	private WebPage addWebPageSegmentation(WebPage webPage) {
		SegmentationFactory segmentFactory = new SegmentationFactory(webPage.getDoc());
		WebPageSection webPageSections = new WebPageSection();

		webPageSections.addAllSegments(segmentFactory.getSegments());
		webPage.setWebPageSegments(webPageSections);

		return webPage;
	}

	public static String makePageSegmentation(WebPage webPage){
		String pageSegmentation = "";

		pageSegmentation += webPage.getTitle();

		for(Cluster segment : webPage.getWebPageSegments().segments){
			pageSegmentation += segment.getSegmentText()+"\n --------- \n";
		}

		return pageSegmentation;
	}

	public static String makePageSummarization(WebPage webpage){
		String pageSummarization = "";

		pageSummarization += webpage.getTitle();
		pageSummarization += webpage.getSummary()+"\n";
		pageSummarization += webpage.getLinks()+"\n";

		return pageSummarization;
	}

	public static String makePageStructurization(WebPage webpage){

		String pageStructurization = "";
		pageStructurization += webpage.getTitle()+"\n";

		for(Link link : webpage.getWebPageStructure().getLinks()){

			if(link.getURL().contains(webpage.getDomainName())){
				pageStructurization += link.getURL()+"\n";
				System.out.println("EQUAL!!"+link.getURL()+" -- "+webpage.getDomainName());
			}
			else{
				System.out.println("NOT EQUAL!!"+link.getURL()+" -- "+webpage.getDomainName());
			}
		}

		return pageStructurization;
	}

	public static String extractedLinkedDocs(WebPage webPage){
		String linkedDocs = "";
		linkedDocs += webPage.getTitle()+"\n";

		for(Link link : webPage.getWebPageStructure().getLinks()){
			int pos = link.getURL().lastIndexOf(".");
			int posLastSlash = link.getURL().lastIndexOf("/");

			if(!link.getURL().contains(".html") && pos != -1 && pos > posLastSlash){	
				System.out.println("["
						+link.getURL().substring(pos+1, link.getURL().length()).toUpperCase()+"]");
			}
		}	
		return linkedDocs;
	}


	public static void main(String args[]){
		Document doc = Utilities.getDoc("http://boston.lti.cs.cmu.edu/classes/95-865/HW/HW2/");
		WebPage webPage = new WebPage(doc);

		WebSummaryFactory webSummaryFactory = new WebSummaryFactory();
		webPage = webSummaryFactory.addWebPageProperties(webPage);

		webPage = webSummaryFactory.addWebPageSegmentation(webPage);
		System.out.println(webSummaryFactory.makePageStructurization(webPage));

		extractedLinkedDocs(webPage);


		System.out.println(webSummaryFactory.makePageSegmentation(webPage));
		System.out.println(webSummaryFactory.makePageSummarization(webPage));
	}


}
