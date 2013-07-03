package run;

import index.InverseDocumentFreq;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import contentalignment.Cluster;
import contentalignment.Segment;

import similarityalgos.CharacterBasedDistribution;
import utilities.DataWriter;
import utilities.GraphExtender;
import utilities.Utilities;
import websummary.AlignmentFactory;
import websummary.FeatureExtractor;
import websummary.HistoryVisualizer;
import websummary.WebSummaryFactory;
import websummary.WebTask;
import document.WebPage;
import document.WebPageSection;

public class WebSummaryRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		String[] clips = new String[]{
				"E-mail: sdumais@microsoft.com Mail: One Microsoft Way, Redmond WA 98052-6399, USA",
		"I am interested in algorithms and interfaces for improved information retrieval, as well as general issues in human-computer interaction. I joined Microsoft Research in July 1997. I work on a wide variety of information access and management issues, including: personal information management, web search, question answering, information retrieval, text categorization, collaborative filtering, interfaces for improved search and navigation, and user/task modeling. Prior to coming to Microsoft, I worked on a statistical method for concept-based retrieval known as Latent Semantic Indexing. You can find pointers to this work on the Bellcore (now Telcordia) LSI page.",
				"S. Kairam, J.T. Teevan, M. Morris, D. Liebling and S.T. Dumais (2013).  Towards supporting search over trending events with social media.  In Proceedings of ICWSM 2013. K. Radinsky, K. Svore, S.T. Dumais, J. Teevan and E. Horvitz (2013).  Behavioral dynamics on the web: Learning, modeling and predicting.  To appear in ACM:TOIS. Kato, M., J. Teevan, R.W. White and S.T. Dumais (2013).  Clarifications and question specificity in synchronous social Q&A. In Proceedings of CHI 2013 (Work in Progress). A. Ligne, E. Adar, J. Teevan and S.T. Dumais (2013).  Predicting citation counts using text and graph mining.  In Proceedings of iConference 2013, Workshop on Computational Scientometrics: Theory and Application."};
		
		
		for(int i=0; i<clips.length;i++){
			
			Segment _segment = new Segment(clips[i], null); 
			Cluster cluster = new Cluster();
			cluster.addSegment(_segment);
			WebPageSection webPageSection = new WebPageSection();
			webPageSection.addCluster(cluster);

			//Need to add segment name
			webPageSection.setSectionSize(cluster.getSegmentText().split(" ").length);
			CharacterBasedDistribution distribution = new CharacterBasedDistribution(cluster.getSegmentText());
			webPageSection.setDistrbution(distribution.distribution);
			webPageSection.setPageLocation((i)/clips.length);
			
			webPageSection.setPos(i);
			Cluster tempCluster = new Cluster();
			tempCluster.setPos(i);
			tempCluster.addWebPageSection(0, webPageSection);
			AlignmentFactory.alignedContent.add(tempCluster);
		}
		
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
		
		FeatureExtractor fex = new FeatureExtractor(webPages);
		fex.extract();
		List<String> features = fex.getFeatures();
		
		for(String str : features){
			System.out.println(str);
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
