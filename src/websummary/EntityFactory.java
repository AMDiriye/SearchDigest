package websummary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import namedentities.NamedEntity;
import namedentities.NamedEntityExtractor;
import contentalignment.SegmentationFactory;

import document.WebPage;
import document.WebPageEntity;

import text.TextProcessor;
import utilities.StopWordCollection;

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
		
		List<NamedEntity> namedEntities = EntityFactory.generateEntities(text);	
		for(NamedEntity namedEntity : namedEntities){
			System.out.println(namedEntity.getEntityValue() +" -- "+namedEntity.getType());
			webPageEntity.addWebPageEntity(namedEntity.getEntityValue());
		}
			
	}

	public static List<NamedEntity> generateEntities(String text){
		List<NamedEntity> entities = new ArrayList<NamedEntity>();
		
		text = text.trim();
		
		NamedEntityExtractor namedEntityExtractor  = new NamedEntityExtractor();;
		ArrayList<NamedEntity> namedEntities = namedEntityExtractor.findNamedEntities(text);
		 
		
		for(NamedEntity ent : namedEntities){
			 
			if(goodEntity(ent,namedEntities)){
				entities.add(ent);
				//System.out.println(ent.getEntityValue() + " -- "+ent.getType());
			}
		}
		return entities;
	}
	
	
 	private static boolean goodEntity(NamedEntity entity, List<NamedEntity> namedEntities){
		
		String entityValue = entity.getEntityValue().trim();
		
		if(entityValue.replaceAll("[^a-zA-Z]", "").length() == 0)
			return false;
		
		StopWordCollection stopWordCollection = StopWordCollection.getInstance();
		
		if(stopWordCollection.removeStopWords(entityValue).length() == 0)
			return false;	
		
		for(NamedEntity ent : namedEntities){
			if(ent.getEntityValue().contains(entityValue) && !ent.equals(entity))
				return false;
		}
		
		return true;
	} 
 	
	public WebPageEntity getWebPageEntity(){
		return webPageEntity;
	}
	
	
	
}
