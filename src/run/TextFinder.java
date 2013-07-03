package run;

import java.util.List;

import namedentities.EntityFactory;
import namedentities.NamedEntity;

import org.jsoup.nodes.Document;

import utilities.Utilities;
import document.Text;
import document.WebPage;

public class TextFinder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		WebPage webPage = new WebPage(Utilities.getDoc("http://www.reuters.com/finance/stocks"));
		Document doc = webPage.getDoc();
		String docText = webPage.getContent();
		
		Text text = new Text(docText,0);
		
		//Named Entity Recogn
		List<NamedEntity> namedEntities = EntityFactory.generateEntities(webPage.getContent());
		
		for(NamedEntity namedEntity : namedEntities){
			System.out.println(namedEntity.getEntityValue() +" -- "+namedEntity.getType());
		}
		
	}

}
