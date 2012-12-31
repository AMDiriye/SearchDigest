package contentminer;

import java.io.IOException;
import java.util.ArrayList;

public class Programme {


	@SuppressWarnings("unused")
	public static void main(String args[]) throws IOException{

		WebPageMiner webPageMiner = new WebPageMiner();
		WebPage webPage1 = webPageMiner.mine("http://jeffhuang.com/");
		WebPage webPage2 = webPageMiner.mine("http://en.wikipedia.org/wiki/Euclidean_norm#Euclidean_norm");

		ComparisonEngine compEngine = new ComparisonEngine();

		/*
		ArrayList<WebPageEntity> allPageEntities = webPage1.getAllPageEntities();

		for(WebPageEntity webPageEntity : allPageEntities){
			//WebPageEntity relatedPageEntity = compEngine.findSimilarContent(webPageEntity, webPage2,0.05);

			if(relatedPageEntity != null)
				System.out.println(webPageEntity.namedEntities.toString()+" \n---> "+relatedPageEntity.toString());
		}
		 */

		ArrayList<String> allPageEntities = removeDuplicates(webPage1.getAllPageNamedEntities());

		for(String namedEntity : allPageEntities){
			WebPageEntity relatedPageEntity = compEngine.findMatchingContent(namedEntity, webPage2,0.05);

			System.out.println(namedEntity);
			
			if(relatedPageEntity != null)
				System.out.println(namedEntity +" \n---> "+relatedPageEntity.toString());
		}
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

}
