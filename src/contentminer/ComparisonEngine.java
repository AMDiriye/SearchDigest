package contentminer;

import java.util.ArrayList;

public class ComparisonEngine {

	public ComparisonEngine(){}

	public WebPageEntity findSimilarContent(WebPageEntity webPageEntity, WebPage webPage, double threshold){
		
		double highestSim = 0.0;
		WebPageEntity relatedEntity = null;
		ArrayList<WebPageEntity> allPageEntities = webPage.getAllPageEntities();
		
		for(WebPageEntity tempWebPageEntity : allPageEntities){
			
			double tempSim = overlapSimilarity(tempWebPageEntity.terms,webPageEntity.terms);
			
			if(tempSim >= highestSim){
				relatedEntity = tempWebPageEntity;
				highestSim = tempSim;
			}
		}

		if(highestSim >= threshold)
			return relatedEntity;
		else return null;
	}
	
	
	public WebPageEntity findMatchingContent(WebPageEntity webPageEntity, WebPage webPage,double threshold){
		double highestSim = 0.0;
		WebPageEntity relatedEntity = null;
		ArrayList<String> allPageNamedEntities = webPage.getAllPageNamedEntities();
		ArrayList<WebPageEntity> allPageEntities = webPage.getAllPageEntities();
		
		for(WebPageEntity tempWebPageEntity : allPageEntities){
			
			double tempSim = overlapSimilarity(tempWebPageEntity.terms,allPageNamedEntities);
			
			if(tempSim >= highestSim){
				relatedEntity = tempWebPageEntity;
				highestSim = tempSim;
			}
		}

		if(highestSim >= threshold)
			return relatedEntity;
		
		else return null;
		
	}
		

	private double overlapSimilarity(ArrayList<String> entity1, ArrayList<String> entity2) {
		double similarity = 0.0;
	
		for(String term : entity1) {
			if (term != "" && entity2.contains(term))
				similarity++;
		}
		return similarity/Math.min(entity1.size(),entity2.size());
	}

	private double cosineSimilarity(ArrayList<String> entity1, ArrayList<String> entity2){
		double similarity = 0.0;

		
		for(String category : entity1)
		{
			if (category != "" && entity2.contains(category))
			{
				similarity++;
			}
		}

		return 1-Math.cos(similarity / Math.sqrt((entity1.size() * entity2.size())));
	}

	private double jaccardSimilarity(ArrayList<String> entity1, ArrayList<String> entity2){
		double similarity = 0.0;
		
		for(String category : entity1)
		{
			if (category != "" && entity2.contains(category))
				similarity++;
		}
		return similarity / (entity1.size() +entity2.size() - similarity);
	}

	private double diceSimilarity(ArrayList<String> entity1, ArrayList<String> entity2){
		double similarity = 0.0;

		for(String category : entity1)
		{
			if(category != "" && entity2.contains(category))
				similarity++;
		}
		return similarity / (entity1.size() + entity2.size());
	}

}
