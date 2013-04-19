package websummary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import document.WebPage;
import document.WebPageEntity;

import text.TextProcessor;

public class EntityFactory {

	WebPageEntity webPageEntity;
	
	public EntityFactory(WebPage webPage){
		webPageEntity = new WebPageEntity();
		extractEntities(webPage.getDoc().text());
	}
	
	public EntityFactory(WebPage[] webPages){
		webPageEntity = new WebPageEntity();
		
		for(WebPage webPage : webPages){
			extractEntities(webPage.getDoc().text());
		}
		
	}
	
	//TODO: Still need to extract named entities
	private void extractEntities(String text) {
		TextProcessor tp = new TextProcessor();
		tp.process(text);
		
		webPageEntity.addAllWebPageEntity(Arrays.asList(tp.getDates()));
		webPageEntity.addAllWebPageEntity(Arrays.asList(tp.getPhoneNumbers()));
		webPageEntity.addAllWebPageEntity(Arrays.asList(tp.getEmails()));		
	}

	public WebPageEntity getWebPageEntity(){
		return webPageEntity;
	}
	
	
	
}
