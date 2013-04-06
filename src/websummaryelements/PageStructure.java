package websummaryelements;

import java.util.ArrayList;
import java.util.List;

public class PageStructure {

	List<Link> links;
	List<String> pageSections;
	String pageTitle;
	String pageURL;
	String summary;
	
	public PageStructure(){
		links = new ArrayList<Link>();
		pageSections = new ArrayList<String>();
	}
	
	public void addLink(String url, String text){
		links.add(new Link(url, text));
	}
	
	public void pageSection(String pageSection){
		pageSections.add(pageSection);
	}
	
	public void setPageTitle(String pageTitle){
		this.pageTitle = pageTitle;
	}
	
	public void setSummary(String summary){
		this.summary = summary;
	}
	
	class Link{
		String url;
		String text;
		
		public Link(String url, String text){
			this.url = url;
			this.text = url;
		}
	}
	
}
