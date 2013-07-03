package contentalignment;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import document.WebPageSection;

import similarityalgos.CharacterBasedDistribution;
import utilities.Utilities;


public class Cluster {

	List<Segment> segments;
	List<Cluster> clusters;
	List<WebPageSection> webPageSections;
	List<String> terms;
	double locationSim;
	List<Double> pageLocations;
	int state = 0;
	String text;
	String url;
	int pos;
	public static int numAlignments;
	
	public Cluster(){
		this.url = url;
		this.segments = new ArrayList<Segment>();
		this.terms = new ArrayList<String>();
		this.clusters = new ArrayList<Cluster>();
		this.webPageSections = new ArrayList<WebPageSection>();
		locationSim = 0;
		pageLocations = new ArrayList<Double>();
		this.text = "";
	}

	public double getSimilarity(Segment segment){
		return Utilities.jaccardSimilarity(segment.getCleanTextList(),terms);
	}

	public double getSimilarity(Cluster cluster){
		CharacterBasedDistribution doc1 = new CharacterBasedDistribution(cluster.text);
		CharacterBasedDistribution doc2 = new CharacterBasedDistribution(text);
		double sim =Utilities.jsDivergence(doc1.distribution,doc2.distribution);
		System.out.println("************************");
		System.out.println(cluster.text);
		System.out.println("-------vs------");
		System.out.println(text);
		System.out.println(sim);
		return Utilities.diceSimilarity(cluster.getCleanTextList(),terms);
	}

	public double getSimilarity(WebPageSection cluster) {
		double cosineSim = Utilities.cosineSimilarity(cluster.getProcessedText().split(" "),terms.toArray(new String[]{})); 
		CharacterBasedDistribution doc1 = new CharacterBasedDistribution(cluster.getText());
		CharacterBasedDistribution doc2 = new CharacterBasedDistribution(text);
		double characterSim =Utilities.jsDivergence(doc1.distribution,doc2.distribution);
		
		double simMax = cosineSim+characterSim;
		
		return simMax ;//+ (1-(locationSim/((double)webPageSections.size())))*simMax;
	}
	
	public void addSegment(Segment segment){
		segments.add(segment);
		terms.addAll(segment.cleanTextList);
		//processedTerms += segment.processedTerms;
		text += " "+segment.text;
	}
	
	public void addCluster(int index, Cluster cluster){
		//Adds empty clusters if there is no alignment
		if(index > clusters.size()){
			for(int i=clusters.size();i<index;i++)
				clusters.add(i, new Cluster());
		}
		clusters.add(index, cluster);
		segments.addAll(cluster.getSegments());
		terms.addAll(cluster.getCleanTextList());
		//processedTerms += cluster.getProcessedText();
		text += " "+cluster.text;
	}

	public void addWebPageSection(int index, WebPageSection webPageSection){
		//Adds empty clusters if there is no alignment
		if(index > webPageSections.size()){
			for(int i=webPageSections.size();i<index;i++)
				webPageSections.add(i, new WebPageSection());
		}
		webPageSections.add(index, webPageSection);
		segments.addAll(webPageSection.getAllSegments());
		terms.addAll(webPageSection.getCleanTextList());
		//processedTerms += webPageSection.getProcessedText();
		text += " "+webPageSection.getText();
		
		for(Double d : pageLocations){
			locationSim += Math.abs(d-webPageSection.getRelativePageLocation());
		}
		
		pageLocations.add(new Double(webPageSection.getRelativePageLocation()));
		
	}
	
	public boolean isSameCluster(Segment segment){

		if(segments.size() < 1)
			return true;

		else if(!isSameType(segment.node))
			return false;

		return true;
	}

	/* State 0 = new section with no content
	 * State 1 = section with content
	 */
	private boolean isSameType(Node node){
	
		if(state == 0){
			
			Node node2 =  segments.get(segments.size()-1).node;
			
			if(!hasSameParent(node,node2)){
				state = 0;
				return false;
			}
			else if(!isHeading(getTagName(node2))){
				
				if(isHeading(getTagName(node)) || !hasSameParent(node,node2)){
					return false;
				}				
				state = 1;
				return true;
			
			}
			else if(isHeading(getTagName(node))){
				return true;
			
			}
			else {
				
				
				state = 1;
				return true;
			}
		}
		
		if(state == 1){
			
				int pos = segments.size() > 1?segments.size() -1:1;

				Node elem2 =  segments.get(pos).node;
				
				if(node.attr("class").equalsIgnoreCase(elem2.attr("class")) && getTagName(node).equalsIgnoreCase(getTagName(elem2))){ 
					return true;
				}
				
				
				else{
					state = 0;
					return false;
				}
			
		}
		
		return false;
	}


	private boolean hasSameParent(Node node, Node node2) {
		Node tempNode = node;
		
		if(getTagName(node).equals(getTagName(node2)) || node.parent().equals(node2.parent()))
			return true;
			
		while(tempNode.parent() != null){
			
			if(tempNode.parent().equals(node2) || tempNode.parent().siblingNodes().contains(node2)){
				return true;
			}
			tempNode = tempNode.parent();
		}
		return false;
	}

	private String getTagName(Node node){
		if(node instanceof Element)
			return ((Element)node).tagName();
		
		return "";
	}
	//TODO: Better way to identify headings in webpages
	private boolean isHeading(String tagName){
		if(tagName.equalsIgnoreCase("h1") ||tagName.equalsIgnoreCase("h2")||tagName.equalsIgnoreCase("h3")||tagName.equalsIgnoreCase("h4")
				|| tagName.equalsIgnoreCase("h5")||tagName.equalsIgnoreCase("h6"))
			return true;
		else return false;
	}

	
	private boolean containsTextNode(Node _node){
		
		if(_node instanceof TextNode ){
			int length = ((TextNode) _node).text().replaceAll("[^A-Za-z0-9]", "").length();
			
			if(length > 0)
				return true;
			
			else return false;
		}
		
		for(Node childNode : _node.childNodes()){
			if(childNode.childNodes().size() > 0){
				if(containsTextNode(childNode))
					return true;
			}
			else{
				if(childNode instanceof TextNode ){
					int length = ((TextNode) childNode).text().replaceAll("[^A-Za-z0-9]", "").length();
					
					if(length > 0)
						return true;
				}
			}
		}
		return false;
	}
	
	public double getPurity(){
		Hashtable<String, Integer> segmentLabels = new Hashtable<String, Integer>();
		int max = 0;
		for(Segment seg : segments){
			if(segmentLabels.containsKey(seg.label.trim())){
				int count = segmentLabels.get(seg.label).intValue()+1;
				
				if(count > max)
					max = count;
				
				segmentLabels.put(seg.label, new Integer(count)); 
			}
			
			else{
				segmentLabels.put(seg.label.trim(), new Integer(1));
			}
		}
		return (double) max /((double)segments.size());
	}
	
	
	@Override
	public String toString(){
		String str = "";
		for(Segment segment : segments)
			str += segment.getText()+" ";
		
		return str;
	}

	public String getCleanText() {
		String str = "";
		for(String term : terms)
			str += term+" ";
		
		return str;
	}

	public List<String> getCleanTextList() {
		return terms;
	}
//	public String getProcessedText(){
//		return processedTerms;
//	}

	public List<Segment> getSegments(){
		return segments;
	}

	public boolean isSame(Cluster cluster) {
		for(Segment segment: cluster.getSegments()){
			if(!segments.contains(segment))
				return false;
		}
		return true;
	}

	public List<Cluster> getClusters() {
		return clusters;
	}

	public String getText() {
		return text;
	}
	
	public List<WebPageSection> getWebPageSections(){
		return webPageSections;
	}
	
	public String getSegmentText(){
		String segmentText = "---";
		
		for(Segment segment : segments){
			segmentText += segment.getText()+"\n --- \n";
		}
		
		return segmentText;
	}
	
	public void setPos(int pos){
		this.pos = pos;
	}

	public int getPos(){
		return pos;
	}
	
	public void setSegmentAlignedFlag() {
		for(WebPageSection webPageSection : webPageSections){
			webPageSection.aligned();
		}
	}

}