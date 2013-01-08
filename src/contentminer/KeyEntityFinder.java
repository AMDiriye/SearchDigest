package contentminer;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;


public class KeyEntityFinder {

	Elements doc;
	ArrayList<Node> nodeList = new ArrayList<Node>();
	ArrayList<String> passageList = new ArrayList<String>();

	public KeyEntityFinder(Elements doc){
		this.doc = doc;
	}

	public int getKeyEntities(String term){

		List<String> passages = new ArrayList<String>();
		List<Node> Nodes = new ArrayList<Node>();
		
		for(Node node : doc){
			passages.addAll(getSentences(node));
		}

		Passage[] rankedPassages = rankPassages(term, passages.toArray(new String[0]));

		for(Passage p : rankedPassages){
			if(p.score == 0.0)
				break;

				
				int indx = passageList.indexOf(p.text);
				
				Node tempNode = nodeList.get(indx);
				
				if(tempNode.toString() != doc.select("body").toString() && !isChild(tempNode)){

				nodeList.get(indx).attr("style", "border-style:dashed; border-color:red;");
				nodeList.get(indx).attr("title", term+" ");
				
				return indx;
				}

		}
		
					
		return -1;
	} 

	private boolean isChild(Node node){
		
		for(int i =0; i < doc.size(); i++){
			if(node.toString() == doc.get(i).toString())
				return true;
		}
		return false;
	}
	//case where there's text in parent
	private List<String> getSentences(Node node){

		ArrayList<String> passages = new ArrayList<String>();
		String docElementText ="";
		ArrayList<String> nodePassages = (ArrayList<String>) makePassages(getText(node), "[.][\\s]"); 
		Node lastNode = node;

		if(node.childNodes().size() == 0){
			return nodePassages;
		}

		for(Node docElement : node.childNodes()){

			if(docElement instanceof Comment)
				continue;

			docElementText += getText(docElement);
			int isSuitable = isSuitablePassage(docElementText);
			lastNode = docElement;
			//Text is just right
			if(isSuitable == 0){
				List<String> tempPassages = makePassages(docElementText, "[.][\\s]");

				if(tempPassages.size() < 2){
					if(!tempPassages.get(0).contains(".\\s")){
						passages.add(docElementText);
						addNodeElement(docElementText, docElement);
						docElementText = "";
					}	//else split and add
					else{
						String[] newPassages = tempPassages.get(0).split("[.][\\s]");

						for(String _tempPassage : newPassages){
							passages.add(_tempPassage);
							addNodeElement(_tempPassage, docElement);
						}

						docElementText = "";
					}
				} 
				else{
					for(String passage : tempPassages){

						if(isSuitablePassage(passage) == 0){
							passages.add(passage);
							addNodeElement(passage, docElement);
							docElementText = "";
						}
						else{
							docElementText +=" "+passage;
						}
					}
				}
			}

			//Text is too long
			else if(isSuitable == 1){
				List<String> tempPassages = makePassages(docElementText.trim(), "[.][\\s]");
				if(docElement.childNodes().size() > 0){
					passages.addAll(getSentences(docElement));
					docElementText = "";
				}

				else{
					for(String passage : tempPassages){

						if(isSuitablePassage(passage) == 0){
							passages.add(passage);
							addNodeElement(passage, docElement);
							docElementText = "";
						}
						else{
							docElementText +=" "+passage;
						}
					}
				}

			}

			//Text is too short
			else if(isSuitable == -1){

				if(docElement.siblingIndex() == node.childNodes().size()){
					passages.add(getText(docElement));
				}
				else{
					docElementText +=" ";
				}
			}

		}

		if(docElementText.replaceAll("[\\s]", "").length() > 0){
			passages.add(docElementText);
			addNodeElement(docElementText, lastNode);
		}

		//String[] passages = makePassages(text, "[.][\\s]");


		return passages;
	}

	private void addNodeElement(String docElementText, Node docElement) {
		passageList.add(docElementText);
		nodeList.add(docElement);

	}

	//What if init text is too small?
	private List<String> makePassages(String text, String delimiter){
		ArrayList<String> passages = new ArrayList<String>();
		String[] tempPassages = text.split(delimiter);
		String tempPassage = "";

		for(String passage : tempPassages){
			int isSuitable = isSuitablePassage(tempPassage);
			tempPassage += " " + passage;

			if(isSuitable == 0){
				passages.add(tempPassage);
				tempPassage = "";
			}
			else if(tempPassage.split(" ").length >= 30 && passage.split(" ").length <= 30){
				passages.add(tempPassage.replace(passage, ""));
				passages.add(passage);
				tempPassage = "";
			}

			else if(isSuitable == 1){

				if(!tempPassage.equals(passage))
					passages.add(tempPassage.replace(passage, ""));

				String newDelim;

				if(delimiter == "[.][\\s]"){
					newDelim = "[,|;|\\n|\\r]";
				}
				else{
					newDelim = " ";
				}

				List<String> newPassages = makePassages(passage, newDelim);

				for(String newPassage : newPassages){
					passages.add(newPassage);
				}
				tempPassage = "";
			}
		}

		if(tempPassage.replaceAll("[\\s]","").length() > 0)
			passages.add(tempPassage);

		return passages;
	}

	private int isSuitablePassage(String passage){
		int passageLength = passage.split(" ").length;
		return passageLength > 5 && passageLength <=30 && passage.length() >25? 0:passageLength  > 30? 1:-1;
	}

	private String getText(Node node){
		return node instanceof TextNode? ((TextNode) node).text(): ((Element) node).text();
	}

	private Passage[] rankPassages(String terms, String[] passages){
		ArrayList<Passage> rankedPassages = new ArrayList<Passage>();
		String stemmedTerms = Utilities.stem(terms);

		for(String passage : passages){
			String procPassage = Utilities.stem(Utilities.removeStopWords(passage));
			double score = Utilities.diceSimilarity(Arrays.asList(stemmedTerms.split("[\\s]")), Arrays.asList(procPassage.split("[\\s]")));

			rankedPassages.add(new Passage(passage, procPassage, score));
		}

		Collections.sort(rankedPassages, new RankComparator());

		return rankedPassages.toArray(new Passage[0]);
	}


	public class RankComparator implements Comparator<Passage>{

		@Override
		public int compare(Passage a, Passage b){
			return a.score > b.score? -1 : a.score == b.score ? 0 : 1;
		}
	}


	private class Passage{
		String text;
		String stemmedText;
		double score;

		public Passage(String text, String stemmedText, double score){
			this.text = text;
			this.stemmedText = stemmedText;
			this.score = score;
		}

		public String toString(){
			return score+" - "+text;
		}
	}
}
