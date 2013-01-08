package contentminer;

import java.util.ArrayList;

import org.jsoup.nodes.Document;

public class WebPage {

	String url;
	String title;
	ArrayList<WebPageEntity> webPageEntities;
	Document doc;
	
	public WebPage(String url){
		this.url = url;
		webPageEntities = new ArrayList<WebPageEntity>();
	}
	
	
	public void addWebPageEntity(WebPageEntity webPageEntity){
		webPageEntities.add(webPageEntity);
	}
	
	public ArrayList<WebPageEntity> getAllPageEntities(){
		ArrayList<WebPageEntity> allPageEntities = new ArrayList<WebPageEntity>();
		
		for(WebPageEntity webPageEntity: webPageEntities){
			
			if(webPageEntity.terms.size() != 0){
				allPageEntities.add(webPageEntity);
			}
			
			if(webPageEntity.children.size() != 0){
				allPageEntities.addAll(webPageEntity.getChildEntities());
			}
			 
		}
		return allPageEntities;
	}
	
	public ArrayList<NamedEntity> getAllPageNamedEntities(){
		ArrayList<NamedEntity> allPageNamedEntities = new ArrayList<NamedEntity>();
		
		
		for(WebPageEntity webPageEntity: webPageEntities){
			
			if(webPageEntity.namedEntities != null && webPageEntity.namedEntities.size() != 0){
				allPageNamedEntities.addAll(webPageEntity.namedEntities);
				
			}
						 
		}
		
		return allPageNamedEntities;
	}
	
	public void addDoc(Document doc){
		this.doc = doc;
	}
	
}
