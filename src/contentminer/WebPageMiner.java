package contentminer;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;



public class WebPageMiner {
	
	
	public static void main(String args[]){
		Document doc;
		try {
			doc = Jsoup.connect("http://google.com/").get();
			String title = doc.title();
			String body = doc.body().text();
			System.out.println(title);
			System.out.println(body);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
