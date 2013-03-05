package run;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;

import utilities.DataWriter;
import utilities.HtmlProcessor;
import utilities.Utilities;

import contentalignment.AlignmentEngine;
import contentalignment.Cluster;
import contentalignment.EntityExtractor;
import contentalignment.Segment;
import contentalignment.SegmentationFactory;
import contentalignment.WebPage;


public class AlignPagesIntoTable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SegmentationFactory segmentFactory = new SegmentationFactory("http://research.microsoft.com/en-us/um/people/pauben/");
		EntityExtractor entityExtractor = new EntityExtractor(segmentFactory.getSegments(), segmentFactory.getDoc());

		List<Cluster> clusters = entityExtractor.getCluster();
		Document doc = HtmlProcessor.addDOMHighlighting(segmentFactory.getDoc(), clusters);

		String alignedContent = makeTable("Sucessfully Aligned Content", new ArrayList<String>(),new ArrayList<String>());
		String unalignedContent = makeTable("Unaligned Content", new ArrayList<String>(),new ArrayList<String>(){});
		String templateContent = "";
		String HTML = "<body>"+alignedContent+unalignedContent+"</body></html>";
		
		FileInputStream fstream;
		
		try {
			fstream = new FileInputStream("./html/template.html");
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String tempString;
			  //Read File Line By Line
			  while ((tempString = br.readLine()) != null){
				  templateContent +=tempString;
			  }
			  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
		DataWriter.writeFile("../html/contentoutput.html", templateContent+HTML);
		Utilities.openFileInBrowser("html/contentoutput.html");

		for(Cluster cluster : clusters){
			System.out.println(cluster.toString());
			System.out.println();
		}

	}


	private static String makeTable(String heading, List<String> titles, List<String> entities){
		
		String HTML = "<h1>"+heading+"</h1>";
		String rows ="<table id=\"table-6\"><thead>";

		for(String str : titles){
			rows +="<tr><th>"+
					str+
					"</th></tr>";
		}

		rows+="</thead><tbody>";

		for(String str : entities){
			rows += "<tr><td>"+
					str+
					"</td></tr>";
		}

		HTML += rows+"</tbody></table>";		

		return HTML;			
	}
	
	
}
