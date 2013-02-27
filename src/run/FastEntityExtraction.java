package run;


import text.TextProcessor;
import contentalignment.SegmentationFactory;

public class FastEntityExtraction {

	
	public static void main(String args[]){
		SegmentationFactory segmentFactory = new SegmentationFactory("http://research.microsoft.com/en-us/um/people/pauben/");
		String text = segmentFactory.getDoc().text();
		
		TextProcessor tp = new TextProcessor();
		tp.process(text);
		

		printList("Dates",tp.getDates());
		printList("Phone numbers",tp.getPhoneNumbers());
		printList("Percentages",tp.getPercentages());
		printList("money", tp.getMoney());
		printList("emails", tp.getEmails());
		
	}
	
	private static void printList(String type, String[] str){
		
		if(str == null)
			return;
		System.out.println(type+": ");
		for(int i=0;i<str.length;i++){
			System.out.print(str[i]+", ");
		}
		System.out.println();
	}
}
