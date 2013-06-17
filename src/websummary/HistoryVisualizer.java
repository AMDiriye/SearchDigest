package websummary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utilities.GraphExtender;
import utilities.Utilities;

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
		webSummary += createHubPagesCard(historyProcessor.domains);
		//webSummary += createAlignedContentCard(historyProcessor.alignedContent);
		webSummary += createWebPageSummaryCard(webPages);
		//webSummary += createWebPageEntitiesContentCard(historyProcessor.webPageEntities);
		//webSummary += createWebPageClusterCard(historyProcessor.webPageClusters);
		webSummary += createRelatedGraphPages(webPages);
	}


	private String createRelatedGraphPages(WebPage[] webPages) {
		String content = "";

		GraphExtender extender = new GraphExtender(webPages);
		List<String> links = extender.getNewLinksGenerated();
		return content;
	}


	private String  createWebPageSummaryCard( WebPage[] webPages){
		String content = "";
		int count = 0;
		int num=0;
		for(WebPage webPage : webPages){
			
			String clusterContent ="";
			if(webPage.getSegmentedWebPage().size() > 1){
				
				clusterContent += addImgs(webPage,count++);//"<img width=\"75px\" src=\""+webPage.getImg()+"\">";
				String[] listOfWebPageSection = new String[Cluster.numAlignments];
				for(WebPageSection webPageSection: webPage.getSegmentedWebPage()){

					if(webPageSection.isAligned()){
						String  _clusterContent = "<li>"+webPageSection.getPos()+" - "+addMoreDetailsDiv(num++,webPageSection.toString())+"</li>";
						listOfWebPageSection[webPageSection.getPos()] = _clusterContent; 
					}
				}
				
				for(int i=0;i<listOfWebPageSection.length;i++){
					
					if(listOfWebPageSection[i] == null){
						clusterContent +="<li style=\"background-color: rgb(255, 204, 0);\">"+i+" -  </li>";
					}
					else{
						clusterContent += listOfWebPageSection[i];
					}	
				}
				
				if(clusterContent!=""){
					clusterContent =getLinksOnSameDomain(webPage)+clusterContent;
					clusterContent = startDiv+clusterContent+endDiv;
				}
				content += "<br\\><ul>"+clusterContent+"</ul>";
			}
		}
		return "<h3>WebPage Summaries</h3>"+content;
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
			webPageClusterContent += startDiv+"\n WebPageCluster \n"+webPageCluster+endDiv;
		}
		return "<h3>Recommended Clusters</h3>"+startDiv+webPageClusterContent+endDiv;
	}

	private String createHubPagesCard(String[] webPages){

		String hubPageString="<ul>";

		for(String webPage : webPages){
			hubPageString += "<li><div style=\"height:25px; width:100px;padding:2px;background-color:white;border:1px solid black;text-align:center\">" +
					"<div style=\"height:25px;width:90px;background-color:rgb(26,98,180);\"></div></div>" +
					"<span id=\"title\">"+webPage+"</span>&nbsp;<span id=\"url\">"+"</span></li>";
		}
		hubPageString +="</ul>";
		return "<h3>Important Pages</h3><div id=\"innerContent\" class=\"hubPages\">"+hubPageString+endDiv;
	}

	private String getLinksOnSameDomain(WebPage webPage){
		String links ="";

		if(webPage.htmlLinks.size() > 0){
			links += "<b>Structure</b><br/>";
		}

		for(String link : webPage.htmlLinks){
			int posOfTopLevelName =  webPage.getURL().lastIndexOf("/");
			String topLevelName = webPage.getURL().substring(0,posOfTopLevelName);

			//System.out.println(webPage.getURL()+ " vs. "+link.attr("abs:href"));
			if(link.contains(topLevelName)){
				links += link+"<br/>";
			}
		}

		return links;
	}

	public String getProcessedHistory() {

		return "<h2>"+title+"</h2>"+webSummary+"";
	}
	
	
	public String addImgs(WebPage webPage, int count){
		String imgs= "<div class=\""+count+"\" id=\"myslide\"><div class=\"cover\" style=\"left: 0px;\">";
		String buttons="<div class=\""+count+"\" id=\"button\">";
		int num=1;
		
		for(Element element : webPage.imgLinks){
			
			if(webPage.imgLinks.indexOf(element) == 0)
				buttons +="<a class=\"button"+num+" active\" rel=\""+num+"\" href=\"#\"></a>";
			else 
				buttons +="<a class=\"button"+num+"\" rel=\""+num+"\" href=\"#\"></a>";
			
			num++;
			
			imgs +="<div class=\"mystuff\"><img src=\""+element.attr("abs:src")+"\" width=\"75px\"></div>";
		}
		
		buttons +="</div>";
		imgs +="</div></div>";
		return buttons+imgs;

	}
	
	
	public String addMoreDetailsDiv(int num, String text){
		String[] textTokens = text.split("[\\s]");
		int count=0;
		String visibleText ="";
		String hiddenText ="";
		
		for(int i=0;i<textTokens.length;i++){
			count +=textTokens[i].length();
			if(count<=100){
				visibleText+=textTokens[i]+" ";
			}
			else{
				hiddenText +=textTokens[i]+" ";
			}
		}	
		return visibleText+"<span id=\"txt-"+num+"\" style=\"display:none;\">"+hiddenText+"</span><span class=\"moreDetails\"  id=\""+num+"\">More...</span>";
	}
	

	public static void main(String args[]){
		Document doc;
		doc = Utilities.getDoc("http://research.microsoft.com/en-us/um/people/sumitb/");
		Elements links = doc.select("a[href]");
		System.out.println(links.size());
		for (Element link : links) {
			System.out.println(link.attr("abs:href")+ " "+ link.text());
		}
	}

}
