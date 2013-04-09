package websummaryelements;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utilities.Utilities;

import net.sf.classifier4J.summariser.SimpleSummariser;
import document.WebPage;

public class WebSummaryFactory {


	public WebPage addWebPageSummarization(WebPage webpage){
		SimpleSummariser summariser = new SimpleSummariser();
		String summary = summariser.summarise(webpage.getContent(), 1);

		webpage.setSummary(summary);
		return webpage;
	}


	public WebPage addWebPageLinks(WebPage webpage){

		Document doc = webpage.getDoc();
		Elements links = doc.select("a[href]");
		webpage.setLinks(links);
		
		return webpage;
	}
	
	public WebPage addWebPageMedia(WebPage webpage){

		Document doc = webpage.getDoc();
		Elements media = doc.select("[src]");
		webpage.setMedia(media);
		
		return webpage;
	}
	
	//TODO: Need to add way of extracting structure based on segments
	public WebPage addWebPageStructure(WebPage webpage){
		
		Elements elements = webpage.getDoc().select("h1,h2,h3,h4,h5,h6");
		webpage.addStructure(elements);
		
		return webpage;
	}



	public static String makePageSummarization(WebPage webpage){
		String pageSummarization = "";
		
		pageSummarization += webpage.getTitle();
		pageSummarization += webpage.getSummary();
		pageSummarization += webPage.getLinks();
		pageSummarization += webPage.getSections();
		
		return pageSummarization;
	}


}
