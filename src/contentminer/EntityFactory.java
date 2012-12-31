package contentminer;

import java.util.ArrayList;

import net.sf.classifier4J.SimpleClassifier;
import net.sf.classifier4J.summariser.SimpleSummariser;

public class EntityFactory {
	
	public static ArrayList<String> generateEntities(String text){
		ArrayList<String> entities = new ArrayList<String>();
		
		text = text.trim();
		
		NamedEntityExtractor namedEntityExtractor  = new NamedEntityExtractor();;
		ArrayList<NamedEntity> namedEntities = namedEntityExtractor.findNamedEntities(text);
		 
		
		for(NamedEntity ent : namedEntities){
			 
			
			if(goodEntity(ent,namedEntities)){
				entities.add(ent.getEntityValue());
				System.out.println(ent.getEntityValue() + " -- "+ent.getType());
			}
		}
		
		
		return entities;
	}

	private static boolean goodEntity(NamedEntity entity, ArrayList<NamedEntity> namedEntities){
		
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
	
}
