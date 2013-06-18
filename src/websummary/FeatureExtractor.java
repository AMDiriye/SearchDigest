package websummary;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import document.WebPage;

public class FeatureExtractor {

	WebPage[] webPages;
	List<String> features;
	List<Integer> occurrences;
	
	public FeatureExtractor(WebPage[] webPages){
		this.webPages = webPages;
		features = new ArrayList<String>();
		occurrences = new ArrayList<Integer>();
	}
	
	public void extract(){
		List<String> features = new ArrayList<String>();
		
		for(WebPage webPage : webPages){
			Elements subHeadings = webPage.getDoc().select("h1,h2,h3,h4,h5,h6");
			
			for (Element subHeading : subHeadings) {
				System.out.println("Headings: "+subHeading.text());
				features.add(subHeading.text());
			}
		}
		
	}
	
	public  List<String>  getFeatures(){
		return features;
	}
}
