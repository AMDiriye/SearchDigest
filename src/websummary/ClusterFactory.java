package websummary;

import java.util.ArrayList;
import java.util.List;

import contentalignment.Cluster;
import document.WebPage;
import document.WebPageCluster;


/*This class is responsible for finding similar webpages in a user's search history 
 *It is assumed session or task boundaries have already been processed 
 */
public class ClusterFactory {

	WebPage[] webPages;
	List<WebPageCluster> webPageClusters;
	final double CLUSTER_THRESHOLD = 0.25;
	
	public ClusterFactory(WebPage[] webpages){
		this.webPages = webpages;
		webPageClusters = new ArrayList<WebPageCluster>();
		seedWebPageClusters();
	}
	
	
	private void seedWebPageClusters() {
		if(webPages[0].getNumInLink() == webPages[webPages.length-1].getNumInLink())
			return;
				
		double maxNumClusters = Math.floor((webPages.length/2));
		
		for(int i=0; i<maxNumClusters;i++){
			WebPageCluster webPageCluster = new WebPageCluster(webPages[i]);
			webPageClusters.add(webPageCluster);
		}
	}


	private void clusterWebPagesBySite(){
		
		for(WebPage webPage: webPages){
			
			for(WebPageCluster webPageCluster : webPageClusters){
				if(webPageCluster.isDomainFoundInCluster(webPage.getDomainName())){
					webPageCluster.addWebPage(webPage);
				}
				continue;
			}
			WebPageCluster webPageCluster = new WebPageCluster(webPage);
			webPageClusters.add(webPageCluster);
		}
	}

	public void clusterWebPagesBySimilarity(){
		WebPageCluster webPageCluster;
		
		for(int i=0; i< webPages.length; i++){
			int closestCluster = findIndexMostSimilarCluster(webPages[i]);
			
			if(closestCluster!=-1){
				webPageCluster = webPageClusters.get(closestCluster);
				webPageCluster.addWebPage(webPages[i]);
			}
			else{
				webPageCluster = new WebPageCluster(webPages[i]);
				webPageClusters.add(webPageCluster);
			}
		}
	}
	
	private int findIndexMostSimilarCluster(WebPage webPage){
		int closestCluster = -1;
		double highestClusterSimVal = 0.0;
		
		for(int i=0; i<webPageClusters.size(); i++){
			double currentClusterSimVal = webPageClusters.get(i).getSimilarity(webPage);
			
			if(currentClusterSimVal > highestClusterSimVal && currentClusterSimVal > CLUSTER_THRESHOLD){
				highestClusterSimVal = currentClusterSimVal;
				closestCluster = i;
			}
		}
		
		return closestCluster;
	}
	
	
	public List<WebPageCluster> getWebPageClusters(){
		return webPageClusters;
	}
}
