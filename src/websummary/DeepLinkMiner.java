package websummary;

import java.util.List;

import org.jsoup.nodes.Document;

import utilities.Utilities;

import document.Link;
import document.WebPage;

public class DeepLinkMiner {

	WebPage webPage;
	
	public DeepLinkMiner(WebPage webPage){
		this.webPage = webPage;
		extractDeepLinks();
	}
	
	private void extractDeepLinks(){
		List<Link> links = webPage.getLinks();
		
		for(Link link : links){
			System.out.println(link.getText());
		}
		
	}

}
