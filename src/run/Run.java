package run;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import utilities.DataWriter;
import utilities.Utilities;
import websummary.HistoryProcessor;
import websummary.HistoryVisualizer;
import document.WebPage;

public class Run {

	public static void main(String args[]){
		
	}
	
	public static void runSearchHistory(String args[]){
		WebPage[] webPages = new WebPage[args.length];
		
		//make new webpages
		for(int i=0; i<args.length; i++){
			webPages[i] = new WebPage(Utilities.getDoc(args[i]));
		}
		
		HistoryProcessor historyProcessor = new HistoryProcessor(webPages);
		historyProcessor.process();
		List<Object> historyItems = historyProcessor.getProcessedHistory();
		
		HistoryVisualizer historyVisualizer = new HistoryVisualizer();
		historyVisualizer.processHistory(historyItems);
		
		String processedHistory = historyVisualizer.getProcessedHistory();
		String content = Utilities.getFileContent("./html/template.html");
		DataWriter.writeFile("../html/contentoutput.html", content+processedHistory);
		Utilities.openFileInBrowser("html/contentoutput.html");
	}
}
