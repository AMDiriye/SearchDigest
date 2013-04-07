package evaluation;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;

 

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
	
	public List<String> getLabels(){
		return labels;
	}
	
	public int getContentSize(){
		return content.size();
	}
	
	public String getContentAt(int pos){
		return content.get(pos);
	}
	
	public String getLabelAt(int pos){
		return labels.get(pos);
	}
	
	
	public String getAllContent(){
		String contentStr = "";

		for(String str : content){
			contentStr += " "+str;
		}	
		return contentStr;
	}


	public boolean containsLabel(String label) {
		return labels.contains(label);
	}


	public List<String> getContent() {
		// TODO Auto-generated method stub
		return content;
	}
}
