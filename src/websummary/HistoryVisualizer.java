package websummary;

import java.util.List;

import contentalignment.Cluster;

import document.WebPage;
import document.WebPageCluster;
import document.WebPageEntity;

public class HistoryVisualizer {
	
	String webSummary;
	String startDiv = "<div id=\"innerContent\">";
	String endDiv = "<\\div>";
	String title;


	public void processHistory(WebPage[] webPages) {
		webSummary = "";
		HistoryProcessor historyProcessor = new HistoryProcessor(webPages);
		historyProcessor.process();
		title = createTitle(webPages);
	}

	private String createTitle(WebPage[] webPages){
		//TODO need to create title;
		return "untitled";
	}
	
	private String createAlignedContentCard(List<Cluster> alignedContent){
		String content="";
		
		for(Cluster cluster : alignedContent){
			content +=cluster.getSegmentText()+"\n";
		}		
		return startDiv+content+endDiv;
	}
	
	private String createWebPageEntitiesContentCard(WebPageEntity webPageEntities){
		return startDiv+webPageEntities.toString()+endDiv;
		
	}
	
	/**
	 * @param webPageEntities
	 * @return the content of clustered documents wrapped around HTML
	 */
	private String createWebPageClusterCard(List<WebPageCluster> webPageClusters){
		
		String webPageClusterContent = "";
		
		for(WebPageCluster webPageCluster : webPageClusters){
			webPageClusterContent += "\n WebPageCluster \n"+webPageCluster+"\n ------- \n";
		}
		return startDiv+webPageClusterContent+endDiv;
		
	}
	
	
	public String getProcessedHistory() {
		
		return "<div id=\"content\" class=\"photo\"><h2>"+title+"<\\h2>"+webSummary+"<\\div>";
	}

}
