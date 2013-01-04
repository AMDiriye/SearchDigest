package contentminer;

import java.util.ArrayList;

public class ComparisonEngine {

	public ComparisonEngine(){}

	public WebPageEntity findSimilarContent(WebPageEntity webPageEntity, WebPage webPage, double threshold){
		
		double highestSim = 0.0;
		WebPageEntity relatedEntity = null;
		ArrayList<WebPageEntity> allPageEntities = webPage.getAllPageEntities();
		
		for(WebPageEntity tempWebPageEntity : allPageEntities){
			
			double tempSim = Utilities.overlapSimilarity(tempWebPageEntity.terms,webPageEntity.terms);
			
			if(tempSim >= highestSim){
				relatedEntity = tempWebPageEntity;
				highestSim = tempSim;
			}
		}

		if(highestSim >= threshold)
			return relatedEntity;
		else return null;
	}
	
	
	public WebPageEntity findMatchingContent(String entity, WebPage webPage, double threshold){
		
		
		if(entity.trim().equalsIgnoreCase("Computer Systems Science")){
			System.out.println("here");
		}
		
		double highestSim = 0.0;
		WebPageEntity relatedEntity = null;
		ArrayList<WebPageEntity> allPageEntities = webPage.getAllPageEntities();
		ArrayList<String> loneEntity = new ArrayList<String>();
		loneEntity.add(entity);
		
		for(WebPageEntity tempWebPageEntity : allPageEntities){
			
			double tempSim = Utilities.cosineSimilarity(tempWebPageEntity.terms,loneEntity);
						
			if(tempSim >= highestSim){
				relatedEntity = tempWebPageEntity;
				highestSim = tempSim;
			}
		}

		if(highestSim >= threshold)
			return relatedEntity;
		
		else return null;
		
	}

}
