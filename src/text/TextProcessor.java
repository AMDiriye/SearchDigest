package text;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProcessor {

	String[] percentages;
	String[] money;
	String[] dates;
	String[] emails;
	String[] phoneNumbers;
	String[] numbers;
	String[] letters;

	public TextProcessor(){}

	public void process(String text){
		phoneNumbers = processPhoneNumber(text);
		percentages = processNumbers(text);
		emails = processEmail(text);
		dates = processDate(text);
		money = processMoney(text);
		percentages = processPercentage(text);
		letters = processLetters(text);
		numbers = processNumbers(text);
	}
	
	private String[] processPercentage(String text) {
		return getMatchingItems("[-]?[0-9]+[.]?[/]?[0-9]*[\\s]*([%]|(percent))",
				text);
	}


	private String[] processMoney(String text) {
		return getMatchingItems("[$|¥|£|€]?[\\d]{1,3}(,[\\s]?[\\d]{3})*(.[\\s]*[\\d]{2})?[\\s]*" +
				"((pound)|(dollar)|(euro)|[$]|[¥]|[£]|[€])", text);
	}


	private String[] processDate(String text) {
		return getMatchingItems("^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$", text);
	}

	private String[] processPhoneNumber(String text) {
		return getMatchingItems("\\d{3}-\\d{3}-\\d{4}", text);
	}
	
	private String[] processNumbers(String text) {
		return getMatchingItems("[\\d]", text);
	}

	private String[] processEmail(String text){
		return getMatchingItems("\\b[\\w.%-]+@(.)+\\.[A-Za-z]{2,4}\\b", text);
	}
	
	private String[] processLetters(String text){
		return getMatchingItems("[A-Za-z]", text);
	}

	private String[] getMatchingItems(String regex, String text){
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		List<String> items = new ArrayList<String>();
		
		while (m.find()) {
			items.add(m.group());
		}
		
		return items.toArray(new String[]{});
	}
	
	public String[] getDates(){
		return dates;
	}
	
	public String[] getPhoneNumbers(){
		return phoneNumbers;
	}
	
	public String[] getEmails(){
		return emails;
	}
	
	public String[] getMoney(){
		return money;
	}
	
	public String[] getPercentages(){
		return percentages;
	}

	public String[] getNumbers() {
		return numbers;
	}
	
	public String[] getLetters() {
		return letters;
	}
	
}
