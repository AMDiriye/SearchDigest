package contentminer;

import java.io.IOException;
import java.util.ArrayList;

public class Programme {

	
	public static void main(String args[]) throws IOException{

		WebPageMiner webPageMiner = new WebPageMiner();
		WebPage webPage2 = webPageMiner.mine("http://research.microsoft.com/en-us/um/people/ryenw/index.html");
		WebPage webPage1 = webPageMiner.mine("http://research.microsoft.com/en-us/groups/clues");
	
		ComparisonEngine compEngine = new ComparisonEngine();
		
		ArrayList<WebPageEntity> allPageEntities = webPage1.getAllPageEntities();
		
		for(WebPageEntity webPageEntity : allPageEntities){
			WebPageEntity relatedPageEntity = compEngine.findMatchingContent(webPageEntity, webPage2,0.25);
			
			if(relatedPageEntity != null)
				System.out.println(webPageEntity.text+" \n---> "+relatedPageEntity.toString());
		}
		
		
	}
	
	
}
