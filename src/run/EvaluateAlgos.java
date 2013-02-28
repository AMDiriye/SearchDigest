package run;

import index.InverseDocumentFreq;
import index.InverseSegmentFreq;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import namedentities.EntityFactory;
import namedentities.NamedEntity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import evaluation.Data;
import evaluation.Evaluator;

import similarityalgos.MutualInformation;
import utilities.Utilities;


public class EvaluateAlgos {


	public static void main(String args[]){
		//amendFiles();
		File[] files = new File("test data").listFiles();
		List<Data> data = new ArrayList<Data>();

		for(File file : files){
			data.add(makeData(file.getAbsolutePath(),false));
		}

		double precision = 0;
		double recall = 0;
		double totalInstances = 0;

		for(int i=0; i<data.size();i++){

			for(int j=i+1; j<data.size();j++){

				if(i!=j){
					System.out.println((totalInstances++)+" of "+(Math.pow(data.size(),2)-data.size())/2);
					List<String> generatedLabels = getLabelsGenerated(data.get(i),data.get(j));
					//System.out.println("--------");
					//System.out.println(data.get(i).getAllContent().substring(0,100));
					//System.out.println(data.get(j).getAllContent().substring(0,100));

					//System.out.println("Precision: "+Evaluator.getPrecision(data.get(i), generatedLabels) +
					//		" Recall: " +Evaluator.getRecall(data.get(i), generatedLabels));

					precision += Evaluator.getPrecision(data.get(i), data.get(j), generatedLabels);
					recall += Evaluator.getRecall(data.get(i), data.get(j), generatedLabels);

				}
			}
		}
		precision = (precision/totalInstances);
		recall = (recall/totalInstances);

		System.out.println("precision: "+precision+" -- recall: "+recall);
	}


	public static List<String> getLabelsGenerated(Data doc1, Data doc2){

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
				double tempSim = Utilities.diceSimilarity(Arrays.asList(doc1Terms.split(" ")), Arrays.asList(doc2Terms.split(" ")));
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


	private static List<String> getBestLabels(List<double[]> segmentSimVals, List<String> labels) {
		String[] segmentLabels = new String[segmentSimVals.size()];
		List<Integer> labelledDocs = new ArrayList<Integer>();
		List<Integer> labelledRows = new ArrayList<Integer>();
		
		for(int i=0; i<segmentSimVals.size(); i++){
			double biggestSimVal = 0;
			int posLabel = -1;
			int posSegment = -1;

			//this section finds the current max value
			//represents the labels
			for(int j=0; j<segmentSimVals.size(); j++){
				double[] segmentSimVal = segmentSimVals.get(j);

				//if it's null continue onto the next label
				if(labelledRows.contains(j))
					continue;

				//represents the segments
				for(int k =0; k<segmentSimVal.length; k++){

					if(segmentSimVal[k]>=biggestSimVal && !labelledDocs.contains(k)){
						biggestSimVal = segmentSimVal[k];
						posLabel = k;
						posSegment = j;
					}
				}

			}
			if(posSegment != -1 && posLabel != -1){
				segmentLabels[posSegment] = labels.get(posLabel);
				labelledDocs.add(new Integer(posLabel));
				labelledRows.add(new Integer(posSegment));
			}
			
		}

		return Arrays.asList(segmentLabels);
	}



	private  static Data makeData(String filePath, boolean extractEntities){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document dom = null;

		try {
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			//parse using builder to get DOM representation of the XML file
			dom = db.parse(filePath);
			System.out.println(dom.getChildNodes().getLength());


		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}

		//get the root element
		Element docEle = dom.getDocumentElement();
		Data data = new Data();

		//get a nodelist of 
		NodeList nl = docEle.getChildNodes();
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {

				if(!nl.item(i).getNodeName().equalsIgnoreCase("#text")){

					if(extractEntities){
						List<NamedEntity> namedEntities = EntityFactory.generateEntities(nl.item(i).getTextContent());
						String content="";

						for(NamedEntity namedEntity : namedEntities){
							content += " "+namedEntity.getEntityValue();
							System.out.println(content);
						}

						data.addContent(nl.item(i).getNodeName(),content);
					}
					else {
						data.addContent(nl.item(i).getNodeName(),nl.item(i).getTextContent());
					}
				}
			}
		}
		return data;

	}

	private static void amendFiles(){
		File[] files = new File("E:\\Users\\Rupert\\Desktop\\New folder").listFiles();



		for(File file : files){

			try {

				BufferedReader input =  new BufferedReader(new FileReader(file));
				StringBuilder contents = new StringBuilder();
				contents.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				contents.append("<homepage>");
				contents.append(System.getProperty("line.separator"));

				try {
					String line = null; 

					while (( line = input.readLine()) != null){
						contents.append(line);
						contents.append(System.getProperty("line.separator"));
					}
					contents.append("</homepage>");

					FileWriter fstream = new FileWriter(file.getAbsolutePath()+".xml");
					BufferedWriter out = new BufferedWriter(fstream);
					out.write(contents.toString());
					//Close the output stream
					out.close();

				}
				finally {
					input.close();
				}
			}
			catch (IOException ex){
				ex.printStackTrace();
			}


		}
	}
}
