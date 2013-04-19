package websummary;

import java.util.List;

import contentalignment.Cluster;

import document.WebPage;
import document.WebPageCluster;
import document.WebPageEntity;

public class HistoryVisualizer {
	
	WebPage[] webPages;
	String webSummary;
	
	public HistoryVisualizer(WebPage[] webPages){
		this.webPages = webPages;
		webSummary = "";
	}

	public void processHistory() {
		HistoryProcessor historyProcessor = new HistoryProcessor(webPages);
		historyProcessor.process();
	}

	
	private String createAlignedContentCard(List<Cluster> alignedContent){
		String content="\n----AlignedContent Card----\n";
		
		for(Cluster cluster : alignedContent){
			content +=cluster.getSegmentText()+"\n-x-x-x-x-x--x-x-x-x-x-\n";
		}		
		return content;
	}
	
	private String createWebPageEntitiesContentCard(WebPageEntity webPageEntities){
		String webPageEntityCard = "----WebPageEntity Card----\n";
		return webPageEntityCard+"\n ------- \n"+webPageEntities.toString()+"\n ------- \n";
		
	}
	
	/**
	 * @param webPageEntities
	 * @return the content of clustered documents wrapped around HTML
	 */
	private String createWebPageClusterCard(List<WebPageCluster> webPageClusters){
		
		String webPageClusterContent = "----WebPageCluster Card----\n";
		
		for(WebPageCluster webPageCluster : webPageClusters){
			webPageClusterContent += "\n WebPageCluster \n"+webPageCluster+"\n ------- \n";
		}
		return "\n ------- \n"+webPageClusterContent+"\n ------- \n";
		
	}
	
	
	public String getProcessedHistory() {
		return webSummary;
	}

}
