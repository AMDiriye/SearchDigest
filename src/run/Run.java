package run;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utilities.DataWriter;
import utilities.Utilities;
import websummary.HistoryProcessor;
import websummary.HistoryVisualizer;
import document.WebPage;

public class Run {

	public static void main(String args[]){
		FileInputStream fstream;
		String templateContent="";
		
		File input = new File("/Users/abdigani/Downloads/saved_resource.htm");
		try {
			Document doc = Jsoup.parse(input, "UTF-8");
			Elements links = doc.select("a[href]");
			
			for(Element link : links){
				System.out.println(link.attr("abs:href"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
