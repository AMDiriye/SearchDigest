package run;
import index.InverseDocumentFreq;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
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
import evaluation.Data;


public class AlignPagesIntoTable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SegmentationFactory segmentFactory = new SegmentationFactory("http://research.microsoft.com/en-us/um/people/pauben/");
		EntityExtractor entityExtractor = new EntityExtractor(segmentFactory.getSegments(), segmentFactory.getDoc());
		List<Cluster> clusters = entityExtractor.getCluster();
		
		segmentFactory = new SegmentationFactory("http://research.microsoft.com/en-us/um/people/pauben/");
		entityExtractor = new EntityExtractor(segmentFactory.getSegments(), segmentFactory.getDoc());
		List<Cluster> clusters2 = entityExtractor.getCluster();

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
	
	
	private static List<String> getLabelsGenerated(Data doc1, Data doc2){

		List<double[]> segmentSimVals = new ArrayList<double[]>();
		List<String> labels = new ArrayList<String>();

		String _doc1Terms = Utilities.stem(Utilities.removeStopWords((doc1.getAllContent()).toLowerCase()));
		String _doc2Terms = Utilities.stem(Utilities.removeStopWords(((doc2.getAllContent()).toLowerCase())));

		Utilities.isf = new InverseDocumentFreq(_doc1Terms+ " "+_doc2Terms);

		for(int i=0; i<doc1.getContentSize();i++){

			double bestSim = 0.0;
			int closestContent = -1;
			double[] simVals = new double[doc2.getContentSize()];
			for(int j=0; j<doc2.getContentSize();j++){
				String doc1Terms = Utilities.removeStopWords((doc1.getContentAt(i).toLowerCase()));
				String doc2Terms = Utilities.removeStopWords((doc2.getContentAt(j).toLowerCase()));

				doc1Terms = Utilities.stem(doc1Terms);
				doc2Terms = Utilities.stem(doc2Terms);

				//Change here to test other text-based metrics
				double tempSim = Utilities.jaccardSimilarity(Arrays.asList(doc1Terms.split(" ")), Arrays.asList(doc2Terms.split(" ")));
				//MutualInformation.getValue(doc1Terms,doc2Terms);
				simVals[j] = tempSim;
				if(tempSim > bestSim){
					bestSim = tempSim;
					closestContent = j;
				}
			}

			//System.out.println(closestContent);
			if(closestContent != -1)
				labels.add(doc2.getLabelAt(closestContent));
			else
				labels.add(null);

			segmentSimVals.add(simVals);
		}
		return getBestLabels(segmentSimVals,doc2.getLabels());
	}
	
}
