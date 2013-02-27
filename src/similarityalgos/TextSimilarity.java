package similarityalgos;

import index.InverseSegmentFreq;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TextSimilarity extends Similarity{


	public static double cosineSimilarity(List<String> entity1, List<String> entity2, InverseSegmentFreq isf){
		double similarity = 0.0;


		for(String term : entity1){
			similarity += getTFIDF(term, entity2.toString(), isf);
		}

		return 1-Math.cos(similarity / Math.sqrt((entity1.size() * entity2.size())));
	}


	public static double jaccardSimilarity(List<String> entity1, List<String> entity2){
		double similarity = 0.0;

		for(String term : entity1) {
			similarity += count(term, entity2.toString());
		}

		return similarity / (entity1.size() +entity2.size() - similarity);
	}


	public static double diceSimilarity(List<String> entity1, List<String> entity2){
		double similarity = 0.0;

		for(String term : entity1) {
			similarity += count(term, entity2.toString());
		}

		return similarity / (entity1.size() + entity2.size());
	}


	public static double euclideanDistance(List<String> entity1, List<String> entity2,  InverseSegmentFreq isf){
		double similarity = 0.0;	

		Set<String> allTermDocs = new HashSet<String>();

		allTermDocs.addAll(entity1);
		allTermDocs.addAll(entity2);


		for(String term : allTermDocs){
			similarity += Math.pow(Math.abs(getTFIDF(term, entity1.toString(), isf)-count(term, entity2.toString())),2);
		}

		return Math.sqrt(similarity);
	}
	
	
	public static double calculateKLD(List<String> values,List<String> value2) {  

	    Map<String, Integer> map = new HashMap<String, Integer>();  
	    Map<String, Integer> map2 = new HashMap<String, Integer>();  
	   
	    for (String sequence : values){  
	        if (!map.containsKey(sequence)){  
	            map.put(sequence, 0);
	        }
	        map.put(sequence, map.get(sequence) + 1);
	    }

	    for (String sequence : value2)  {  
	        if (!map2.containsKey(sequence)) {
	            map2.put(sequence, 0);
	        }
	        map2.put(sequence, map2.get(sequence) + 1);
	    }

	    Double result = 0.0;
	    Double frequency2=0.0;
	    for (String sequence : map.keySet()){

	        Double frequency1 = (double) map.get(sequence) / values.size();
	        //System.out.println("Freuency1 "+frequency1.toString());
	        if(map2.containsKey(sequence)){

	            frequency2 = (double) map2.get(sequence) / value2.size();                
	        }
	        result += frequency1 * (Math.log(frequency1/frequency2) / Math.log(2));         
	    }
	    return result/2.4;  
	}    
	



}
