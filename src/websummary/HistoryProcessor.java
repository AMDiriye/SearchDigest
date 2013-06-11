package websummary;

import java.util.ArrayList;
import java.util.List;

import contentalignment.Cluster;

import document.WebPageEntity;

import document.WebPage;
import document.WebPageCluster;

public class HistoryProcessor {

	WebPage[] webPages;
	String[] domains;
	List<WebPageCluster> webPageClusters;
	WebPageEntity webPageEntities;
	List<List<Cluster>> alignedContent;
	AlignmentFactory alignmentFactory;
	
	public HistoryProcessor(WebPage[] webPages) {
		this.webPages = webPages;
	}

	public void process() {
		//create the history items and add to list
		findHubPages();
		findDomains();
		//createPageClusters();
		createPageAlignment();
		//extractEntities();
		//extractQueries();
		//reorder based on importance
		//reorderHistory();
	}

	//Find domain of all links on pages
	private void findDomains() {
		List<String> _domains = new ArrayList<String>();
		List<Double> count = new ArrayList<Double>();
		
		for(WebPage webPage : webPages){
			String[] url = webPage.getURL().split("[/]"); 
			int posOfWebPage = webPage.getURL().indexOf(url[url.length-1]);
			String topDomainName = webPage.getURL().substring(0,posOfWebPage);
			_domains.add(topDomainName);
		}
		
		domains = _domains.toArray(new String[]{});
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
		//ClusterFactory clusterFactory = new ClusterFactory();
		//clusterFactory.setWebPages(webPages);
		//clusterFactory.clusterWebPagesByURL();
		webPageClusters = clusterFactory.getWebPageClusters();
	}
	
	private void createPageAlignment(){
		//First find groups of pages to align
		alignedContent = new ArrayList<List<Cluster>>();
		ClusterFactory clusterFactory = new ClusterFactory();
		clusterFactory.setWebPages(webPages);
		clusterFactory.clusterWebPagesByURL();
		List<WebPageCluster> tempWebPageClusters = clusterFactory.getWebPageClusters();
		
		//Align groups of pages
		for(WebPageCluster webPageCluster : tempWebPageClusters){
			alignmentFactory = new AlignmentFactory(webPageCluster.getWebPages().toArray(new WebPage[]{}));
			alignedContent.add(alignmentFactory.getAlignedWebPages());
		}
		
	}
	
	private void extractEntities(){
		EntityFactory entityFactory = new EntityFactory(webPages);
		webPageEntities = entityFactory.getWebPageEntity();
	}
	
	private void extractQueries(){
		// TODO Auto-generated method stub	
	}

}
