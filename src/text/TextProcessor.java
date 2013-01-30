package text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProcessor {

	String[] percentages;
	String[] money;
	String[] dates;
	String[] phoneNumbers;
	
	public TextProcessor(){}


	public void process(String text){
		processDate(text);
		processMoney(text);
		processPercentage(text);
	}


	private void processPercentage(String text) {
		Pattern p = Pattern.compile("[-]?[0-9]+[.]?[/]?[0-9]*[\\s]*([%]|(percent))");
		Matcher m = p.matcher(text);
		
		while (m.find()) {
		  System.out.println("percentage "+m.group());
		}	
	}


	private void processMoney(String text) {
		Pattern p = Pattern.compile("[$|¥|£|€]?[\\d]{1,3}(,[\\s]?[\\d]{3})*(.[\\s]*[\\d]{2})?[\\s]*((pound)|(dollar)|(euro)|[$]|[¥]|[£]|[€])");
		Matcher m = p.matcher(text);
		
		while (m.find()) {
		  System.out.println("money "+m.group());
		}
	}


	private void processDate(String text) {
		Pattern p = Pattern.compile("([0-9]{1,2}||\\d{4})-([a-zA-Z]{3}||\\d{2})-([0-9]{4}||\\d{2})");
		Matcher m = p.matcher(text);
		
		while (m.find()) {
		  System.out.println("percentage "+m.group());
		}	

	}





}
