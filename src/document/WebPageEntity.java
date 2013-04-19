package document;

import java.util.ArrayList;
import java.util.List;

public class WebPageEntity {

	List<String> webPageEntities;
	
	public WebPageEntity(){
		webPageEntities = new ArrayList<String>();
	}
	
	public void addWebPageEntity(String webPageEntity){
		webPageEntities.add(webPageEntity);
	}
	
	public void addAllWebPageEntity(List<String> webPageEntities){
		this.webPageEntities.addAll(webPageEntities);
	}
	
	public List<String> getWebPageEntities(){
		return webPageEntities;
	}
	
	@Override
	public String toString(){
		String content="";
		
		for(String webPageEntity : webPageEntities)
			content +="* "+webPageEntity+"\n";
		
		return content;
	}
}
