package websummary;

import java.util.Arrays;
import java.util.List;

import contentalignment.Cluster;
import contentalignment.Segment;

import document.WebPage;
import document.WebPageCluster;
import document.WebPageEntity;
import document.WebPageSection;

public class HistoryVisualizer {

	String webSummary;
	String startDiv = "<div id=\"innerContent\">";
	String endDiv = "</div>";
	String title;


	public void processHistory(WebPage[] webPages) {
		webSummary = "";
		HistoryProcessor historyProcessor = new HistoryProcessor(webPages);
		historyProcessor.process();
		title = createTitle(webPages); 
		webSummary += createHubPagesCard(historyProcessor.webPages);
		webSummary += createAlignedContentCard(historyProcessor.alignedContent);
		webSummary += createWebPageSummaryCard(historyProcessor,webPages);
		//webSummary += createWebPageEntitiesContentCard(historyProcessor.webPageEntities);
		//webSummary += createWebPageClusterCard(historyProcessor.webPageClusters);
	}

	private String createTitle(WebPage[] webPages){
		//TODO need to create title;
		return "";
	}

	private String  createWebPageSummaryCard(HistoryProcessor historyProcessor, WebPage[] webPages){
		String content = "";

		List<Cluster> segmentedPages = historyProcessor.alignmentFactory.alignedWebPages;

		for(Cluster segmentedPage: segmentedPages){
			int count =0;
			String clusterContent ="";
			if(segmentedPage.getWebPageSections().size() > 1){
				for(WebPageSection webPageSection: segmentedPage.getWebPageSections()){
					String webPageContent = webPageSection.toString();
					if(webPageContent.length() >0)
						clusterContent += "<li>***"+webPageContent.substring(0, Math.min(webPageContent.length(),100))+"...</li>";
				}

				content += "-----------<br\\><ul>"+clusterContent+"</ul>";
			}
		}

		return "<h3></h3>"+startDiv+content+endDiv;
	}

	private String createAlignedContentCard(List<List<Cluster>> alignedContent){
		String content="";

		for(List<Cluster> clusters : alignedContent){
			String clusterContent="";
			for(Cluster  cluster : clusters){
				String segmentContent ="";
				for(Segment segment : cluster.getSegments()){
					segmentContent+="<p>"+segment.getText()+"</p>";
				}
				clusterContent+=segmentContent+"\n";
			}
			content +=startDiv+clusterContent+endDiv+"\n";
		}		
		return "<h3>Useful Segments</h3>"+startDiv+content+endDiv;
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
			webPageClusterContent += startDiv+"\n WebPageCluster \n"+webPageCluster+"\n ------- \n"+endDiv;
		}
		return "<h3>Recommended Clusters</h3>"+startDiv+webPageClusterContent+endDiv;

	}

	private String createHubPagesCard(WebPage[] webPages){

		String hubPageString="<ul>";

		for(WebPage webPage : webPages){
			hubPageString += "<li><span class=\"star\" id=\"title\">"+webPage.getTitle()+"</span>&nbsp;<span id=\"url\">"+webPage.getURL()+"</span></li>";
		}
		hubPageString +="</ul>";
		return "<h3>Important Pages</h3><div id=\"innerContent\" class=\"hubPages\">"+hubPageString+endDiv;
	}


	public String getProcessedHistory() {

		return "<div id=\"content\" class=\"photo\"><h2>"+title+"</h2>"+webSummary+"</div>";
	}

}
