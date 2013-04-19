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
import contentalignment.PageSegmentGrouper;
import contentalignment.Segment;
import contentalignment.SegmentationFactory;
import evaluation.Data;


public class AlignPagesIntoTable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SegmentationFactory segmentFactory = new SegmentationFactory("http://research.microsoft.com/en-us/um/people/sdumais/");
		PageSegmentGrouper entityExtractor = new PageSegmentGrouper(segmentFactory.getSegments(), segmentFactory.getDoc());
		List<Cluster> cluster1 = entityExtractor.getCluster();

		segmentFactory = new SegmentationFactory("http://research.microsoft.com/en-us/um/people/pauben/");
		entityExtractor = new PageSegmentGrouper(segmentFactory.getSegments(), segmentFactory.getDoc());
		List<Cluster> cluster2 = entityExtractor.getCluster();
		 
		List<List<Cluster>> clusters = new ArrayList<List<Cluster>>();
		
		File[] files = new File("test data").listFiles();
		
		for(int i=0; i<4;i++){
			File file = files[i];
			Data data = EvaluateAlgos.makeData(file.getAbsolutePath(),false);
			List<Cluster> _clusters = new ArrayList<Cluster>();
			
			for(String _segment : data.getContent()){
				Cluster cluster = new Cluster();
				Segment segment = new Segment(_segment);
				cluster.addSegment(segment);
				_clusters.add(cluster);
			}
			clusters.add(_clusters);
		}

		
		// clusters.add(cluster1);
		// clusters.add(cluster2);

		List<Cluster> finalClusters = alignDocs(clusters);


		String alignedContent = makeTable("Sucessfully Aligned Content", clusters.size(),finalClusters);
		//String unalignedContent = makeTable("Unaligned Content", new ArrayList<String>(),new ArrayList<String>(){});
		String templateContent = "";
		String HTML = "<body>"+alignedContent+"</body></html>";

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
	}
	


	private static String makeTable(String heading, int numDocs, List<Cluster> clusters){

		String HTML = "<h1>"+heading+"</h1>";
		String rows ="<table id=\"table-6\"><thead><tr>";

		for(int i =0; i< numDocs; i++){
			rows +="<th>"+
					"doc "+(i+1)+
					"</th>";
		}

		rows+="</tr></thead><tbody>";

		for(Cluster cluster : clusters){
			rows+="<tr>";
			for(Cluster childCluster : cluster.getClusters()){
				rows += "<td>"+
						childCluster.getText()+
						"</td>";
			}
			rows+="</tr>";
		}




		HTML += rows+"</tbody></table>";		

		return HTML;			
	}


	private static List<Cluster> alignDocs(List<List<Cluster>> docs){

		List<Cluster> clusterRows = new ArrayList<Cluster>();

		//iterates through each doc
		for(int i=0; i< docs.size(); i++){
			List<Cluster> doc = docs.get(i);
			List<Integer> rowsUnavailable = new ArrayList<Integer>();

			//find best matching if none add new row
			for(int j=0; j<doc.size(); j++){

				Cluster segment = doc.get(j);

				if(i == 0){
					Cluster tempCluster = new Cluster();
					tempCluster.addCluster(0, segment);
					clusterRows.add(tempCluster);

				}
				else{

					int bestCluster = findBestCluster(segment, clusterRows,rowsUnavailable);

					if(bestCluster == -1){
						Cluster tempCluster = new Cluster();
						tempCluster.addCluster(i, segment);
						clusterRows.add(tempCluster);
						rowsUnavailable.add(new Integer(clusterRows.size()-1));
					}
					else{
						rowsUnavailable.add(new Integer(bestCluster));
						clusterRows.get(bestCluster).addCluster(i, segment);					
					}
				}
			}
		}
		return clusterRows;
	}

	private static int findBestCluster(Cluster cluster, List<Cluster> clusters, List<Integer> rowsUnavailable) {
		double bestSimVal = 0;
		int posBestCluster = -1;

		for(int i = 0;i < clusters.size(); i++){

			double tempSimVal = clusters.get(i).getSimilarity(cluster);

			System.out.println(tempSimVal);

			if(tempSimVal > bestSimVal && !rowsUnavailable.contains(new Integer(i))){
				bestSimVal = tempSimVal;
				posBestCluster = i;
			}
		}
		return posBestCluster;
	}
}
