package evaluation;

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
 

public class Data {

	List<String> content;
	List<String> labels;
	Document dom;
	
	
	public Data(){
		content = new ArrayList<String>();
		labels = new ArrayList<String>();
	}


	public void addContent(String nodeName, String textContent) {
		labels.add(nodeName);
		content.add(textContent);
	}
	
	
	public boolean checkClassification(String textContent, String label){
		int index = content.indexOf(textContent);
		
		return labels.get(index).equalsIgnoreCase(label);
	}
	
	
}
