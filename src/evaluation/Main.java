package evaluation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Main {


	public static void main(String args[]){
		//amendFiles();

		File[] files = new File("E:\\Users\\Rupert\\Desktop\\New folder").listFiles();
		List<Data> data = new ArrayList<Data>();


		for(File file : files){
			data.add(makeData(file.getAbsolutePath()));
		}
	}


	private  static Data makeData(String filePath){
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
					System.out.println(nl.item(i).getNodeName()+ " - "+nl.item(i).getTextContent());
					data.addContent(nl.item(i).getNodeName(),nl.item(i).getTextContent());
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
