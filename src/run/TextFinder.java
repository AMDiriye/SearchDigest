package run;

import org.jsoup.nodes.Document;

import utilities.Utilities;
import document.Text;
import document.WebPage;

public class TextFinder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		WebPage webPage = new WebPage(Utilities.getDoc("http://www.reuters.com/finance/stocks"));
		Document doc = webPage.getDoc();
		String docText = webPage.getContent();
		
		Text text = new Text(docText,0);
		
		
	}

}
