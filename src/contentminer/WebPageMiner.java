package contentminer;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;



/**
 * @author Abdigani Diriye
 *
 */
public class WebPageMiner {
	
	
	public static void main(String args[]) throws IOException{
		
		
		
		
		Document doc;
		try {
			doc = Jsoup.connect("http://google.com/").get();
			String title = doc.title();
			String body = doc.body().text();
			NamedEntityExtractor nee = new NamedEntityExtractor();
			ArrayList<NamedEntity> listNamedEntities = nee.findNamedEntities(body);
			
			for(NamedEntity ne:listNamedEntities){
				System.out.println(ne.getEntityValue()+"-"+ne.getType());
			}
			
			System.out.println(title);
			System.out.println(body);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
