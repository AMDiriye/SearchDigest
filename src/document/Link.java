package document;

public class Link{
	
	String url;
	String text;
	
	public Link(String url, String text){
		this.url = url;
		this.text = url;
	}
	
	public String getURL(){
		return url;
	}
	
	public String getText(){
		return text;
	}
}