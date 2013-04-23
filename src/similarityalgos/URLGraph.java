package similarityalgos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import document.Link;

public class URLGraph {

	HashMap<String,List<Link>> urlGraph;
	List<String> allSources;
	
	public URLGraph(){
		urlGraph = new HashMap();
		allSources = new ArrayList<String>();
	}
	
	public void addURL(String source, List<Link> links){
		allSources.add(source);
		urlGraph.put(source, links);
	}
	
	
	public List<String> getAllSources(){
		return allSources;
	}
	
	public List<Link> getLinks(String source){
		return urlGraph.get(source);
	}
	
	public boolean isSourceMember(String url){
		return allSources.contains(url);
	}
	
	/*public void addURL(String source, String outLink){
		if(urlGraph.containsKey(source)){
			List<String> outLinks = urlGraph.get(source);
			outLinks.add(outLink);
			urlGraph.put(source, outLinks);
		}
		else{
			List<String> outLinks = new ArrayList<String>();
			outLinks.add(outLink);
			urlGraph.put(source, outLinks);
		}
	}*/


}
