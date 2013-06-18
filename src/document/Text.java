package document;

import text.TextProcessor;

public class Text {

	String terms;
	int currLen;
	int emailLen;
	int numLen;
	int textLen;
	int dateLen;
	double pageLocation;
	TextProcessor textProcessor;
	
	public Text(String text, double pageLocation){
		this.terms = text;
		this.pageLocation = pageLocation;
		processText();
	}
	
	private void processText(){
		textProcessor = new TextProcessor();
		textProcessor.process(terms);
		
		//set text characteristics
		//emailLen = textProcessor.getEmails().length;
		//numLen = textProcessor.getNumbers().length;
		currLen = textProcessor.getMoney().length;
		//textLen = textProcessor.getLetters().length;
		//dateLen = textProcessor.getDates().length;
	}
	
	
}
