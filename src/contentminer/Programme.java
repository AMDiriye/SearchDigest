package contentminer;

import java.io.IOException;
import java.util.ArrayList;

public class Programme {

	
	@SuppressWarnings("unused")
	public static void main(String args[]) throws IOException{

		WebPageMiner webPageMiner = new WebPageMiner();
		WebPage webPage2 = webPageMiner.mine("http://research.microsoft.com/en-us/um/people/ryenw/index.html");
		WebPage webPage1 = webPageMiner.mine("http://research.microsoft.com/en-us/groups/clues");
	
		ComparisonEngine compEngine = new ComparisonEngine();
		
		ArrayList<WebPageEntity> allPageEntities = webPage1.getAllPageEntities();
		
		for(WebPageEntity webPageEntity : allPageEntities){
			WebPageEntity relatedPageEntity = compEngine.findMatchingContent(webPageEntity, webPage2,0.15);
			//WebPageEntity relatedPageEntity = compEngine.findSimilarContent(webPageEntity, webPage2,0.25);
			
			if(relatedPageEntity != null)
				System.out.println(webPageEntity.namedEntities.toString()+" \n---> "+relatedPageEntity.toString());
		}
		
		
	}
	
	
}
