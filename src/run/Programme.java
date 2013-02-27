package run;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import namedentities.KeyEntityFinder;
import namedentities.NamedEntity;

import org.jsoup.nodes.Node;

import contentminer.ComparisonEngine;
import contentminer.WebPage;
import contentminer.WebPageMiner;

import utilities.DataWriter;
import utilities.HtmlProcessor;
import utilities.Utilities;

public class Programme {
	
	public static void main(String args[]) throws IOException{

		//comparePages();
		augmentPage();
	}
	
	private static void augmentPage(){
		
		System.out.println("Enter a URL");
		String pageURL=readLine();
		
		WebPageMiner webPageMiner = new WebPageMiner();	
		
		WebPage webPage = webPageMiner.mine(pageURL,false);
		webPage = HtmlProcessor.addDOMHighlighting(webPage);
		DataWriter.writeFile("../html/jhuang.html", webPage.doc.toString());
		Utilities.openFileInBrowser("html/jhuang.html");
	}
	private static void comparePages(){
		System.out.println("Please enter URL for page1");
		String page1URL=readLine();
		
		System.out.println("and now page2");
		String page2URL=readLine();
		
		WebPageMiner webPageMiner = new WebPageMiner();	
		
		WebPage webPage1 = webPageMiner.mine(page1URL,true);
		WebPage webPage2 = webPageMiner.mine(page2URL,false);

		ComparisonEngine compEngine = new ComparisonEngine();

		ArrayList<String> allPageEntities = removeDuplicates(webPage1.getAllPageNamedEntities());
		
		//String[] allPageEntities = Utilities.getTopKTerms(webPage1.doc.text());
		
		KeyEntityFinder kef = new KeyEntityFinder(webPage2.doc.children());
		
		for(String str : allPageEntities)
		{
			int pos = kef.getKeyEntities(str.trim());
			
			if(pos != -1)
			{
				//webPage2.doc.childNode(1).childNode(2).attr("Style", "background-color:#F0FFFF");
			}
			
		}
		
		String entities = "";
		
		for(String str : allPageEntities){
			if(!entities.contains(str))
				entities += str+", ";
		}
		
		String banner = "<div style=\"width: 95%; height: 30px; background-color: white; border: gray; position: fixed; -moz-box-shadow: 3px 3px 5px 6px #ccc;"
				+"box-shadow: 2px 2px 2px 2px gray; text-align: center; font-family: helvetica; padding: 20px; font-weight: bold; overflow: scroll; overflow-x: hidden;z-index: 1000 !important;\">"+
				entities
				+"</div><div style=\"padding-top: 100px;\"></div>";
		
		webPage2.doc.select("body").prepend(banner);
		DataWriter.writeFile("../html/jhuang.html", webPage2.doc.toString());
		Utilities.openFileInBrowser("html/jhuang.html");


		
		/*ArrayList<WebPageEntity> allPageEntities = webPage1.getAllPageEntities();

		for(WebPageEntity webPageEntity : allPageEntities){
			//WebPageEntity relatedPageEntity = compEngine.findSimilarContent(webPageEntity, webPage2,0.05);

			if(relatedPageEntity != null)
				System.out.println(webPageEntity.namedEntities.toString()+" \n---> "+relatedPageEntity.toString());
		}
		 

		ArrayList<String> allPageEntities = removeDuplicates(webPage1.getAllPageNamedEntities());

		for(String namedEntity : allPageEntities){
			WebPageEntity relatedPageEntity = compEngine.findMatchingContent(namedEntity, webPage2,0.05);

			System.out.println(namedEntity);
			
			if(relatedPageEntity != null)
				System.out.println(namedEntity +" \n---> "+relatedPageEntity.toString());
		}
		*/
		//String htmlFilePath = "path/to/html/file.html"; // path to your new file
				//File htmlFile = new File(htmlFilePath);

				//open the default web browser for the HTML page
				//Desktop.getDesktop().browse(htmlFile.toURI());

				//if a web browser is the default HTML handler, this might work too
				//Desktop.getDesktop().open(htmlFile);
	}
	
	private static ArrayList<String> removeDuplicates(ArrayList<NamedEntity> allPageEntities){
		
		ArrayList<String> noDuplicPageEntities = new ArrayList<String>();
		
		for(NamedEntity entity : allPageEntities){
			
			if(!noDuplicPageEntities.contains(entity.getEntityValue().trim())){
				noDuplicPageEntities.add(entity.getEntityValue().trim());
			}
		}
		
		
		return noDuplicPageEntities;
	}
	
	public static String readLine()
	{
		String s = "";
		try {
			InputStreamReader converter = new InputStreamReader(System.in);
			BufferedReader in = new BufferedReader(converter);
			s = in.readLine();
		} catch (Exception e) {
			System.out.println("Error! Exception: "+e); 
		}
		return s;
	}

}
