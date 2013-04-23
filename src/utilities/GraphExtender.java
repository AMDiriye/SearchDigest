package utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import similarityalgos.URLGraph;
import document.Link;
import document.WebPage;

public class GraphExtender {

	WebPage[] webpages;
	URLGraph urlGraph;
	List<String> newLinks;
	final double MIN_LINK_SIM = 0.666; 
	
	
	public GraphExtender(WebPage[] webpages){
		this.webpages = webpages;
		newLinks = new ArrayList<String>();
		makeURLGraph();
		findNewLinks();
	}
	
	private void makeURLGraph(){
		urlGraph = new URLGraph();
		
		for(WebPage webPage : webpages){
			urlGraph.addURL(webPage.getURL(), webPage.getLinks());
		}
	}
	
	private void  findNewLinks(){
	
		for(WebPage webPage : webpages){
			List<Link> webPageLinks = urlGraph.getLinks(webPage.getURL());
			List<Double> simVal = new ArrayList<Double>();
			List<String> links = new ArrayList<String>();
			 
			//find similarity for all its links and selects top 5 links
			for(Link link : webPageLinks){
				double maxSim =0.0;
				WebPage tempWebPage = null;
				
				//finds most similar webPage
				for(WebPage _webPage : webpages){
					
					double tempSim = computeURLSimilarity(link.getURL(),_webPage.getURL());
					
					if(tempSim>maxSim && tempSim > MIN_LINK_SIM && !urlGraph.isSourceMember(link.getURL())){
						maxSim = tempSim;
						tempWebPage = _webPage;
					}
				}
				if(maxSim>0.0){
					simVal.add(new Double(maxSim));
					links.add(link.getURL());
				}
			}
			
			List<String> bestLinks = findBestLinks(links, simVal);
			
			if(bestLinks.size() > 0){
				newLinks.addAll(bestLinks);
			}
		}
	}
	
	
	private List<String> findBestLinks(List<String> links, List<Double> simVals){
		List<String> bestLinks = new ArrayList<String>();
		
		for(int i=0;i<links.size();i++){
			int pos = 0;
			for(int j=0;j<bestLinks.size();j++){
				if(simVals.get(j) > simVals.get(i)){
					pos++;
				}
				else break;
			}
			bestLinks.add(pos, links.get(i));
		}
		
		int size = (bestLinks.size()/2)<5?bestLinks.size():4; 
				
		return bestLinks.subList(0, size);
	}
	
	
	private double computeURLSimilarity(String url1, String url2){
		
		double count = 0.0;
		
		String[] urltokens1 = url1.split("[//]");
		String[] urltokens2 = url2.split("[//]");
		
		for(int i=0;i<urltokens1.length;i++){
			for(int j=0;j<urltokens2.length;j++){
				String urltoken1 = lowerCaseAndTrim(urltokens1[i]);
				String urltoken2 = lowerCaseAndTrim(urltokens2[j]);
				if(urltoken1.equals(urltoken2)){
					count++;
				}
			}
		}
		
		double maxLength = urltokens2.length < urltokens1.length?urltokens1.length:urltokens2.length;
		
		return (count/maxLength);
	}

	private String lowerCaseAndTrim(String str){
		return str.toLowerCase().trim();
	}
	
	public List<String> getNewLinksGenerated(){
		return newLinks;
	}
	
	public static void main(String args[]){
		//GraphExtender ge = new GraphExtender();
		
		//System.out.println(ge.computeURLSimilarity("http://research.microsoft.com/en-us/um/people/alicez/#pubs","http://research.microsoft.com/~alicez/"));
	}
	
}
