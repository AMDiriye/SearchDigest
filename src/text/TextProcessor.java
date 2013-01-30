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
		//processPhoneNumber(text);
		processEmail(text);
//		processDate(text);
	//	processMoney(text);
		//processPercentage(text);
	}


	private void processPercentage(String text) {
		Matcher m = matchPattern("[-]?[0-9]+[.]?[/]?[0-9]*[\\s]*([%]|(percent))",
				text);

		while (m.find()) {
			System.out.println("percentage "+m.group());
		}	
	}


	private void processMoney(String text) {
		Matcher m = matchPattern("[$|¥|£|€]?[\\d]{1,3}(,[\\s]?[\\d]{3})*(.[\\s]*[\\d]{2})?[\\s]*" +
				"((pound)|(dollar)|(euro)|[$]|[¥]|[£]|[€])", text);

		while (m.find()) {
			System.out.println("money "+m.group());
		}
	}


	private void processDate(String text) {
		Matcher m = matchPattern("^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$", text);

		while (m.find()) {
			System.out.println("Date "+m.group());
		}	

	}

	private void processPhoneNumber(String text) {
		Matcher m = matchPattern("\\d{3}-\\d{3}-\\d{4}", text);

		while (m.find()) {
			System.out.println("PhoneNumber "+m.group());
		}
	}

	private void processEmail(String text){
		Matcher m = matchPattern("\\b[\\w.%-]+@(.)+\\.[A-Za-z]{2,4}\\b", text);

		while (m.find()) {
			System.out.println("Email "+m.group());
		};
	}

	private Matcher matchPattern(String regex, String text){
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);

		return m;
	}
}
