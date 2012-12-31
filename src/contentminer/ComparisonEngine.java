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
			
			double tempSim = cosineSimilarity(tempWebPageEntity.terms,loneEntity);
						
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
			
			
			if (term != "" && containsTerm(term,entity2)){
				similarity++;
			}
		}
		return similarity/Math.min(entity1.size(),entity2.size());
	}

	private double cosineSimilarity(ArrayList<String> entity1, ArrayList<String> entity2){
		double similarity = 0.0;

		
		for(String category : entity1)
		{
			if (category != "" && containsTerm(category,entity2))
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
			if (category != "" && containsTerm(category,entity2))
				similarity++;
		}
		return similarity / (entity1.size() +entity2.size() - similarity);
	}

	private double diceSimilarity(ArrayList<String> entity1, ArrayList<String> entity2){
		double similarity = 0.0;

		for(String category : entity1)
		{
			if(category != "" && containsTerm(category,entity2))
				similarity++;
		}
		return similarity / (entity1.size() + entity2.size());
	}

	private boolean containsTerm(String str1, ArrayList<String> terms){
		String[] allTerms = terms.toString().split(" ");		
		
		
		for(String term : allTerms){
			term = Utilities.stem(Utilities.removeStopWords(term.toLowerCase())).trim();
			
			if(term.equals(str1)){
				
				return true;
			}
		}
		return false;
	}
}
