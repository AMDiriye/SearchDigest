package contentminer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;



/**
 * @author Abdigani Diriye
 *
 */
public class WebPageMiner {

	

	public WebPage mine(String url){
		WebPage webPage = new WebPage(url);
		Stemmer stemmer = new Stemmer();

		System.out.println(stemmer.stripAffixes("testing"));

		
		try {
			Document doc = Jsoup.connect("http://research.microsoft.com/en-us/um/people/ryenw/index.html").get();
			List<Node> tx = doc.childNodes();

			for(Node tn:tx){

				int size = tn.childNodes().size();

				for(Node tn1: tn.childNodes()){
					System.out.println("--"+tn1.toString());
					for(Node tn2: tn1.childNodes()){
						System.out.println("--"+tn2.);
					}
				}


			}

			String title = doc.title();
			String body = doc.body().text();
			NamedEntityExtractor nee = new NamedEntityExtractor();
			ArrayList<NamedEntity> listNamedEntities = nee.findNamedEntities(body);

			for(NamedEntity ne:listNamedEntities){
				System.out.println(ne.getEntityValue()+"-"+ne.getType());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return webPage;
	}


	public static void main(String args[]) throws IOException{




	}

}
