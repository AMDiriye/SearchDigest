package document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utilities.Utilities;
import contentalignment.Segment;

public class WebPageCluster {

	List<WebPage> cluster;
	WebPageCentroid centroid;
	
	public WebPageCluster(WebPage webPage){
		cluster = new ArrayList<WebPage>();
		centroid = new WebPageCentroid();
		
		List<String> terms = new ArrayList<String>();
		terms.addAll(webPage.stemmedTerms);
		List<Double> termCounts = new ArrayList<Double>();
		termCounts.addAll(webPage.stemmedTermCounts);
		
		centroid.termCounts = termCounts; 
		centroid.terms = terms;
	}
	
	//TODO: add new cosineSim function
	public double getSimilarity(WebPage webPage){
		return Utilities.cosineSimilarity(centroid.terms,centroid.termCounts, webPage.stemmedTerms,webPage.stemmedTermCounts);
	}
	
	public void addWebPage(WebPage webPage){
		cluster.add(webPage);
		
		for(String term : webPage.stemmedTerms){
			
			if(centroid.terms.contains(term)){
				int index = centroid.terms.indexOf(term);
				Double termCount = centroid.termCounts.get(index);
				
				Double webPageTermCount = webPage.getTermCount(term);
				centroid.termCounts.set(index, termCount+webPageTermCount);
			}
			else{
				centroid.addTerm(term);
				centroid.addTermCount(webPage.getTermCount(term));
			}
			
		}
	}
	
	public void clearWebPage(){
		cluster = new ArrayList<WebPage>();
	}
	
	public boolean isDomainFoundInCluster(String domain){
		
		for(WebPage webPage : cluster){
			if(webPage.getDomainName().contains(domain))
				return true;
		}
		return false;
	}
	
	private void repositionCentroid(){
		List<String> terms = new ArrayList<String>();
		List<Double> termCounts = new ArrayList<Double>();
		
		for(WebPage webPage : cluster){
			for(String term : webPage.stemmedTerms){
				
				if(terms.contains(term)){
					int index = terms.indexOf(term);
					Double termCount = termCounts.get(index);
					
					Double webPageTermCount = webPage.getTermCount(term);
					termCounts.set(index, termCount+webPageTermCount);
				}
				else{
					terms.add(term);
					termCounts.add(webPage.getTermCount(term));
				}
				
			}
		}
	}
	
	@Override
	public String toString(){
		String content ="";
		
		for(WebPage webPage : cluster){
			content += webPage.title+"\n"+webPage.summary+"\n--------\n";
		}
		
		return content;
	}
	
	
	public class WebPageCentroid{
		List<String> terms;
		List<Double> termCounts;
		
		public WebPageCentroid(){
			terms = new ArrayList<String>();
			termCounts = new ArrayList<Double>();
		}
		
		public void addTermCount(Double termCount) {
			termCounts.add(termCount);
			
		}

		public void addDoc(String docText){
			String[] docTerms = docText.split(" ");
			
			for(String docTerm : docTerms){
				if(terms.contains(docTerm)){
					int docTermIndex = terms.indexOf(docTerm);
					Double termCount = termCounts.get(docTermIndex);
					
					termCounts.set(docTermIndex, termCount);
				}
				else{
					terms.add(docTerm);
					termCounts.add(new Double(1));
				}
			}
		}
		
		public void addTerm(String term){
			terms.add(term);
		}	
	}
	
}
