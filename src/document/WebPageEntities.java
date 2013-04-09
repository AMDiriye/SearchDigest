package document;

import java.util.ArrayList;

import namedentities.NamedEntity;
import contentminer.WebPageEntity;

public class WebPageEntities {

	public String url;
	public String title;
	public ArrayList<WebPageEntity> webPageEntities;

	public WebPageEntities(){
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




}
