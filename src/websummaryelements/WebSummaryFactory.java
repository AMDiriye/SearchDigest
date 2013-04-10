package websummaryelements;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utilities.Utilities;

import net.sf.classifier4J.summariser.SimpleSummariser;
import document.WebPage;
import document.WebPageStructure;

public class WebSummaryFactory {


	public WebPage addWebPageProperties(WebPage webPage){
		webPage = addWebPageSummarization(webPage);
		webPage = addWebPageLinks(webPage);
		webPage = addWebPageMedia(webPage);
		webPage = addWebPageStructure(webPage);
		
		return webPage;
	}
	
	private WebPage addWebPageSummarization(WebPage webpage){
		SimpleSummariser summariser = new SimpleSummariser();
		String summary = summariser.summarise(webpage.getContent(), 1);

		webpage.setSummary(summary);
		return webpage;
	}

	

	private WebPage addWebPageLinks(WebPage webpage){

		Document doc = webpage.getDoc();
		Elements links = doc.select("a[href]");
		
		WebPageStructure webPageStructure = new WebPageStructure();
		
		for (Element link : links) {
			webPageStructure.addLink(link.attr("abs:href"), link.text());
		}
		
		webpage.setLinks(links);
		webpage.setWebPageStructure(webPageStructure);
		
		return webpage;
	}
	
	private WebPage addWebPageMedia(WebPage webpage){

		Document doc = webpage.getDoc();
		Elements media = doc.select("[src]");
		webpage.setMedia(media);
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
		
		Elements elements = webpage.getDoc().select("h1,h2,h3,h4,h5,h6");
		webpage.addStructure(elements);
		
		return webpage;
	}



	public static String makePageSummarization(WebPage webpage){
		String pageSummarization = "";
		
		pageSummarization += webpage.getTitle();
		pageSummarization += webpage.getSummary();
		pageSummarization += webpage.getLinks();
		//pageSummarization += webpage.getSections();
		
		return pageSummarization;
	}


	
	public static void main(String args[]){
		Document doc = Utilities.getDoc("http://boston.lti.cs.cmu.edu/classes/95-865/HW/HW2/");
		WebPage webPage = new WebPage(doc);
		
		WebSummaryFactory webSummaryFactory = new WebSummaryFactory();
		webPage = webSummaryFactory.addWebPageProperties(webPage);
		 
		System.out.println(webSummaryFactory.makePageSummarization(webPage));
	}
}
