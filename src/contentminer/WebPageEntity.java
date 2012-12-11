package contentminer;

import java.util.ArrayList;

public class WebPageEntity {

	String text;
	String stemmedText;
	String tag;
	ArrayList<NamedEntity> namedEntities; 
	ArrayList<WebPageEntity> children;
	
	
	public WebPageEntity(){
		children = new ArrayList<WebPageEntity>();
	}
	
	public String getText(){
		return text;
	}
	
	public void addChild(WebPageEntity childEntity){
		children.add(childEntity);
	}
	
	
	
	
}
