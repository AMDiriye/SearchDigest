package run;

import index.InverseDocumentFreq;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utilities.DataWriter;
import utilities.GraphExtender;
import utilities.Utilities;
import websummary.HistoryVisualizer;
import websummary.WebSummaryFactory;
import websummary.WebTask;
import document.WebPage;

public class WebSummaryRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String webTaskContent = "";
		
		//Create webtasks 
		List<WebTask> webTasks = new ArrayList<WebTask>();
		String URLs ="";
		try {
			FileInputStream fstream = new FileInputStream("./data/TaskData.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			WebTask webTask = null;
			
			//Read File Line By Line
			while ((line = br.readLine()) != null){
				
				if(line.trim().length() == 0){
					if(webTask !=null){
						webTasks.add(webTask);
					}
					webTask = null;
				}
				else{
					if(webTask !=null){
						webTask.addURL(line);
					}
					else{
						webTask = new WebTask(line);
					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		WebPage[] webPages = null;
		
		//Iterate through webtasks 
		for(WebTask webtask : webTasks){
			webPages =  new WebPage[webtask.getNumURLs()];
			
			for(int i=0;i<webtask.getNumURLs();i++){
				
				System.out.println(webtask.getURLAt(i));
				WebPage webPage = new WebPage(Utilities.getDoc(webtask.getURLAt(i)));
				webPage.setImg(Utilities.findUserPic(webPage.getDoc().select("img"),webPage.getTitle()));
				webPage = Utilities.addTermInfo(webPage);
				WebSummaryFactory webSummaryFactory = new WebSummaryFactory();
				webPage = webSummaryFactory.addWebPageProperties(webPage);
				webPages[i] = webPage;
			}
			
			InverseDocumentFreq idf = new InverseDocumentFreq();
			
			for(WebPage webPage : webPages){
				
				String stemmedTerms = Utilities.stem(Utilities.removeStopWords(webPage.getContent().replaceAll("[\\.\\/-]", " ")));
				idf.addDocument(stemmedTerms);

			}
			
			Utilities.inverseDocFreq = idf;
			
			HistoryVisualizer historyViz = new HistoryVisualizer();
			historyViz.processHistory(webPages);
			webTaskContent += historyViz.getProcessedHistory();
			break;
			//Find related URLs
			//GraphExtender ge = new GraphExtender(webPages);
			//List<String> newLinks = ge.getNewLinksGenerated();
			
			//for(String newLink : newLinks){
			//	if(!isHTML(newLink))
			//		continue;
			//	newWebPages.add(new WebPage(Utilities.getDoc(newLink)));
			//}
		}
		
		
		//Do something with newLinkDocs
		//newWebPages
		
		
		
		//Get content from template
		FileInputStream fstream;
		String templateContent="";
		
		try {
			fstream = new FileInputStream("./html/webSummarytemplate.html");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String tempString;
			//Read File Line By Line
			while ((tempString = br.readLine()) != null){
				templateContent +=tempString+"\n";
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Add template data
		DataWriter.writeFile("../html/webSummary.html", templateContent+webTaskContent+"</div></body></html>");
		Utilities.openFileInBrowser("html/webSummary.html");
	}
	

	private static boolean isHTMLPage(String url){
		if(url.contains(".wmv")||url.contains(".mp"))
			return false;
		return true;
	}
}
