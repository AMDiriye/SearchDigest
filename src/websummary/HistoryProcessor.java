package websummary;

import java.util.ArrayList;
import java.util.List;

import contentalignment.Cluster;

import document.WebPageEntity;

import document.WebPage;
import document.WebPageCluster;

public class HistoryProcessor {

	WebPage[] webPages;
	//List<Object> processHistory;
	List<WebPageCluster> webPageClusters;
	WebPageEntity webPageEntities;
	List<Cluster> alignedContent;
	
	public HistoryProcessor(WebPage[] webPages) {
		this.webPages = webPages;
	}

	public void process() {
		//create the history items and add to list
		findHubPages();
		createPageClusters();
		createPageAlignment();
		//extractEntities();
		//extractQueries();
		
		//reorder based on importance
		//reorderHistory();
	}

	private void findHubPages() {
		HubLabeller hubLabeller = new HubLabeller(webPages);
		hubLabeller.computeWebPageInLinks();
		hubLabeller.reOrderByInLinks();
		webPages = hubLabeller.getreOrderedPages();
	}

	private void createPageClusters(){
		ClusterFactory clusterFactory = new ClusterFactory(webPages);
		clusterFactory.clusterWebPagesBySimilarity();
		webPageClusters = clusterFactory.getWebPageClusters();
	}
	
	private void createPageAlignment(){
		AlignmentFactory alignmentFactory = new AlignmentFactory(webPages);
		alignedContent = alignmentFactory.getAlignedWebPages();
	}
	
	private void extractEntities(){
		EntityFactory entityFactory = new EntityFactory(webPages);
		webPageEntities = entityFactory.getWebPageEntity();
	}
	
	private void extractQueries(){
		// TODO Auto-generated method stub	
	}

}
