package contentminer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;



/**
 * @author Abdigani Diriye
 *
 */
public class WebPageMiner {

	StopWordCollection stopWordCollection;
	final static NamedEntityExtractor namedEntityExtractor  = new NamedEntityExtractor();
	
	public WebPageMiner(){	
		stopWordCollection = StopWordCollection.getInstance();
	}

	public WebPage mine(String url, boolean performEntityExtraction){

		WebPage webPage = null;
		Document doc;
		WebPageEntity webPageEntity;

		try {

			doc = Jsoup.connect(url).get();
			webPage = new WebPage(url);
			webPage.addDoc(doc);

			Elements docElements = doc.select("body");
			docElements.select("a").unwrap();
						
			for(Element element:docElements){
				webPageEntity = getWebPageEntity(element, performEntityExtraction);

				if(!webPageEntity.text.equals("") )
					webPage.addWebPageEntity(webPageEntity);
			}

		} catch (IOException e) {
			 e.printStackTrace();
		}
		return webPage;
	}

	/*Returns list of web page elements */
	private WebPageEntity getWebPageEntity(Node node, boolean performEntityExtraction) throws IOException{

		WebPageEntity webPageParentEntity = new WebPageEntity(node.toString());

		if (node instanceof TextNode) {
			String nodeText = ((TextNode) node).text();
			System.out.println(((TextNode) node).text());

			if(nodeText.trim().replaceAll("[^a-zA-Z]", "").length()>0){
				webPageParentEntity.text = nodeText;
				webPageParentEntity.stemmedText = Utilities.stem(nodeText);
				webPageParentEntity.stopWordLessText = stopWordCollection.removeStopWords(nodeText);
				webPageParentEntity.addTerms(stopWordCollection.removeStopWords(Utilities.stem(nodeText)));		
				
				if(performEntityExtraction)
					webPageParentEntity.setNamedEntity(namedEntityExtractor.findNamedEntities(nodeText));

			}
		}


		if(node.childNodes().size()>0){

			for(Node childNode: node.childNodes()){
				WebPageEntity webPageChildEntity = new WebPageEntity(childNode.toString());
				//System.out.println("--"+childNode.toString());

				if(childNode != null){

					//webPageChildEntity.addChild(getText(childNode));
					webPageParentEntity.text += getText(childNode);
				}


			}
			if(performEntityExtraction){
				webPageParentEntity.stemmedText = Utilities.stem(webPageParentEntity.text);
				webPageParentEntity.stopWordLessText = stopWordCollection.removeStopWords(webPageParentEntity.text);
				webPageParentEntity.addTerms(stopWordCollection.removeStopWords(Utilities.stem(webPageParentEntity.text)));						
				webPageParentEntity.setNamedEntity(namedEntityExtractor.findNamedEntities(webPageParentEntity.text));
			}

		}
		return webPageParentEntity;
	}


	private String getText(Node node){
		String text = "";

		if(node.childNodes().size() > 0){
			for(Node childNode: node.childNodes()){
				text += getText(childNode);
			}
			return text;
		}
		else{
			if(node != null && node instanceof TextNode && ((TextNode)node).text() != null && !((TextNode)node).text().equals(" ") ){
				return ((TextNode)node).text()+" ";
			}

		}
		return "";

	}
	
}
