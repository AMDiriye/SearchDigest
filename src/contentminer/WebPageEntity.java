package contentminer;

import java.util.ArrayList;

public class WebPageEntity {

	String text;
	String stemmedText;
	String stopWordLessText;
	String tag;
	
	ArrayList<String> terms;
	ArrayList<NamedEntity> namedEntities;
	ArrayList<String> namedEntityTerms;
	ArrayList<WebPageEntity> children;
	
	
	public WebPageEntity(String text){
		this.text = text;
		children = new ArrayList<WebPageEntity>();
		terms = new ArrayList<String>();
		namedEntities = new ArrayList<NamedEntity>();
		namedEntityTerms = new ArrayList<String>();
	}
	
	public String getText(){
		return text;
	}
	
	public void addChild(WebPageEntity childEntity){
		children.add(childEntity);
		namedEntities.addAll(childEntity.namedEntities);
	}

	public void addChildren(ArrayList<WebPageEntity> webPageEntities) {
		children = webPageEntities;
		
		for(WebPageEntity childEntity: webPageEntities){
			namedEntities.addAll(childEntity.namedEntities);
		}
	}
	
	public void addTerms(String entityTerms){
		String[] _entityTerms = entityTerms.split(" ");
		
		for(String term: _entityTerms){
			
			if(term != null && term.replaceAll("[^a-zA-Z]", "").length() > 0){
				//System.out.println("added: "+term);
				terms.add(term);
			}		
		}
	}
	
	public void addNamedEntity(NamedEntity namedEntity){
		namedEntities.add(namedEntity);
	}
	
	
	public void setNamedEntity(ArrayList<NamedEntity> namedEntities){
		this.namedEntities = namedEntities;
		
		for(NamedEntity namedEntity : namedEntities){
			namedEntityTerms.add(namedEntity.getEntityValue());
		}
		
	}
	
	public 	ArrayList<String> getNamedEntities(){
		ArrayList<String> _namedEntities = new ArrayList<String>();
		
		for(WebPageEntity webPageEntity: children){

			if(webPageEntity.namedEntities != null && webPageEntity.namedEntities.size() != 0){
				_namedEntities.addAll(webPageEntity.getNamedEntities());
			}		
		}
		_namedEntities.addAll(namedEntityTerms);
		return _namedEntities;
	} 
	
	public ArrayList<WebPageEntity> getChildEntities(){
		ArrayList<WebPageEntity> childEntities = new ArrayList<WebPageEntity>();
		
		for(WebPageEntity webPageEntity: children){
			
			if(webPageEntity.terms.size() != 0){
				childEntities.add(webPageEntity);
			}
			
			if(webPageEntity.children.size() != 0){
				childEntities.addAll(webPageEntity.getChildEntities());
			}			 
		}
		return childEntities;
	}
	
	public String toString(){
		ArrayList<WebPageEntity> childEntities = getChildEntities();
		String str = "";
		
		for(WebPageEntity webPageEntity: childEntities){
			str += webPageEntity.toString()+"/n";
		}
		
		return str + text;
	}
	
	
}
