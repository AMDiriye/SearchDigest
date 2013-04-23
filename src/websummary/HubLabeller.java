package websummary;

import java.util.ArrayList;
import java.util.List;

import document.Link;
import document.WebPage;

public class HubLabeller {

	WebPage[] webPages;
	public HubLabeller(WebPage[] webPages){
		this.webPages = webPages;
	}
	
	public void computeWebPageInLinks(){
		WebPage webpage1;
		WebPage webpage2;
		
		for(int i=0; i<webPages.length;i++){
			for(int j=i+1;j<webPages.length;j++){
				webpage1 = webPages[i];
				webpage2 = webPages[j];
				List<Link> webPage1Links = webpage1.getWebPageStructure().getLinks();
				List<Link> webPage2Links = webpage2.getWebPageStructure().getLinks();
				
				System.out.println("-------------");
				System.out.println(webpage1.getURL());
				System.out.println(webpage2.getURL());
				
				if(isLinkContained(webPage1Links,webpage2.getURL().trim())){
					webpage2.incrementNumInLink();
				}
				
				if(isLinkContained(webPage2Links,webpage1.getURL().trim())){
					webpage1.incrementNumInLink();
				}
			}
		}
	}
	
	private boolean isLinkContained(List<Link> links, String webPageURL){
		
		for(Link link : links){
			
			if(link.getURL().equalsIgnoreCase(webPageURL))
				return true;
		}
		
		return false;
	}
	
	public void reOrderByInLinks(){
		List<WebPage> reOrderedWebPages = new ArrayList<WebPage>();
		
		for(int i=0;i<webPages.length;i++){
			for(int j=0;j<reOrderedWebPages.size();j++){
				if(webPages[i].getNumInLink() > reOrderedWebPages.get(j).getNumInLink()){
					reOrderedWebPages.add(j, webPages[i]);
					break;
				}
			}
			if(!reOrderedWebPages.contains(webPages[i]))
				reOrderedWebPages.add(webPages[i]);
		}
		webPages = reOrderedWebPages.toArray(new WebPage[]{});
	}
	
	public WebPage[] getreOrderedPages(){
		return webPages;
	}
	
}
