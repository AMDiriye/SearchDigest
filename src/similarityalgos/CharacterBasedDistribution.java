package similarityalgos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharacterBasedDistribution{

	int textSize = 0;
	double[] distribution;

	public CharacterBasedDistribution(String text){
		distribution = new double[7];
		textSize = text.length();
		Arrays.fill(distribution, 0);
		processString(text);
	}

	private void processString(String text){
		
		double increment = ((double)1/textSize);
		
		for(int i=0; i < textSize; i++) {
			char c = text.charAt(i);
			
			if(isCapitalLetter(c)){
				distribution[0] += increment;
			}
			else if(isFullStop(c)){
				distribution[1] += increment;
			}
			else if(isComma(c)){
				distribution[2] += increment;
			}
			else if(isWhiteSpace(c)){
				distribution[3] += increment;
			}
			else if(isDigit(c)){
				distribution[4] += increment;
			}
    	}
		distribution[5] = countNumAbbrs(text)/(double)textSize;
		distribution[6] = countNumDates(text)/(double)textSize;
	}

	public int countNumAbbrs(String text){		
		String regex = "([A-Za-z]\\.)+|([A-Z]{2}+[a-zA-Z0-9\\.']*)";
		return getNumMatching(regex, text);
	}

	public int countNumDates(String text){
		String regex = "^((19|20)\\d\\d)|(\'[0-9]{2})";
		return getNumMatching(regex, text);
	}
	
	private boolean isCapitalLetter(char c){
		return Character.isUpperCase(c);
	}
	
	private boolean isDigit(char c){
		return Character.isDigit(c);
	}

	private boolean isFullStop(char c){
		return c == '.';
	}

	private boolean isComma(char c){
		return c == ',';
	}

	private boolean isWhiteSpace(char c){
		return ((c == ' ') || (c =='\n') || (c == '\t'));
	}
	
	private int getNumMatching(String regex, String text){
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		List<String> items = new ArrayList<String>();
	
		while (m.find()) {
			items.add(m.group());
		}
		return items.size();
	}
	
	/*public static void main(String args[]){
		CharacterBasedDistribution cbd = new CharacterBasedDistribution("2012 1111 '12 SIGIR s.i.gir hi CHI'06 WWW2007 World Wide");
	}*/
}