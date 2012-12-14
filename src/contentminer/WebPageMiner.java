package contentminer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;



/**
 * @author Abdigani Diriye
 *
 */
public class WebPageMiner {

	Stemmer stemmer;
	StopWordCollection stopWordCollection;
	final static NamedEntityExtractor namedEntityExtractor  = new NamedEntityExtractor();;
	
	public WebPageMiner(){
		stemmer  = new Stemmer();
		stopWordCollection = StopWordCollection.getInstance();
	}


	public WebPage mine(String url){

		WebPage webPage = null;
		Document doc;

		try {

			webPage = new WebPage(url);
			doc = Jsoup.connect(url).get();

			List<Node> nodes = doc.childNodes();

			for(Node node:nodes){
				webPage.addWebPageEntity(getWebPageEntity(node));
				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return webPage;
	}



	/*
	 * Returns list of web page elements 
	 */
	private WebPageEntity getWebPageEntity(Node node) throws IOException{

		WebPageEntity webPageParentEntity = new WebPageEntity(node.toString());
		
		if (node instanceof TextNode) {
			String nodeText = ((TextNode) node).text();
			
			if(nodeText.trim().replaceAll("[^a-zA-Z]", "").length()>0){
				webPageParentEntity.text = nodeText;
				webPageParentEntity.stemmedText = stemmer.stripAffixes(nodeText);
				webPageParentEntity.stopWordLessText = stopWordCollection.removeStopWords(nodeText);
				webPageParentEntity.addTerms(stopWordCollection.removeStopWords(stemmer.stripAffixes(nodeText)));		
				webPageParentEntity.setNamedEntity(namedEntityExtractor.findNamedEntities(nodeText));
				
				for(NamedEntity entity:webPageParentEntity.namedEntities)
					System.out.println(entity.getEntityValue()+" - "+entity.getType());
				//System.out.println("------------------------");
				//System.out.println(webPageParentEntity.text);
				//System.out.println(webPageParentEntity.stemmedText);
				//System.out.println(webPageParentEntity.stopWordLessText);
			}
		}


		if(node.childNodes().size()>0){

			for(Node childNode: node.childNodes()){
				WebPageEntity webPageChildEntity = new WebPageEntity(childNode.toString());
				//System.out.println("--"+childNode.toString());

				if(childNode != null){

					webPageChildEntity.addChild(getWebPageEntity(childNode));
				}

				webPageParentEntity.addChild(webPageChildEntity);
			}
		}
		return webPageParentEntity;
	}

}
