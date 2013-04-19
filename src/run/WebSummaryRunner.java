package run;

import utilities.DataWriter;
import utilities.Utilities;
import websummary.HistoryVisualizer;
import websummary.WebSummaryFactory;
import document.WebPage;

public class WebSummaryRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		args=new String[]{"http://research.microsoft.com/en-us/um/people/pauben",
			"http://research.microsoft.com/en-us/um/people/ryenw/",
			"http://research.microsoft.com/en-us/um/people/sdumais/",
			"http://research.microsoft.com/en-us/people/sdumais/",
			"http://research.microsoft.com/en-us/people/sdumais/",
			"http://research.microsoft.com/en-us/",
			"http://research.microsoft.com/en-us/labs/",
			"http://research.microsoft.com/en-us/groups/adapt/"};

		WebPage[] webPages = new WebPage[args.length];
		
		for(int i=0;i<args.length;i++){
			WebPage webPage = new WebPage(Utilities.getDoc(args[i]));
			WebSummaryFactory webSummaryFactory = new WebSummaryFactory();
			webPage = webSummaryFactory.addWebPageProperties(webPage);
			webPages[i] = webPage;
		}
		
		HistoryVisualizer historyViz = new HistoryVisualizer(webPages);
		historyViz.processHistory();
		
		DataWriter.writeFile("../html/webSummary.html", historyViz.getProcessedHistory());
		Utilities.openFileInBrowser("html/webSummary.html");

	}
	

}
