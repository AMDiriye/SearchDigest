package document;

import java.util.ArrayList;
import java.util.List;


public class WebPageStructure {

	List<Link> links;
	List<String> subHeadings;
	
	public WebPageStructure(){
		links = new ArrayList<Link>();
		subHeadings = new ArrayList<String>();
	}
	
	public void addLink(String url, String text){
		links.add(new Link(url, text));
	}
	
	
	public void addSubHeadings(String subHeading){
		subHeadings.add(subHeading);
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
