package websummary;

import java.util.ArrayList;
import java.util.List;

public class WebTask {

	List<String> pageURLs;
	String title;
	
	public WebTask(String title){
		this.title = title;
		pageURLs = new ArrayList<String>(); 
	}

	public void addURL(String url) {
		pageURLs.add(url);
	}
	
	public int getNumURLs(){
		return pageURLs.size();
	}
	
	public List<String> getURLs(){
		return pageURLs;
	}
	
	public String getTitle(){
		return title;
	}

	public String getURLAt(int i) {
		return pageURLs.get(i);
	}
	
}
