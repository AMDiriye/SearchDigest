package similarityalgos;

import index.InverseFreq;
import index.InverseSegmentFreq;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class Similarity {

	static int count(String term, String line){

		Pattern pattern = Pattern.compile(" "+term);
		Matcher matcher = pattern.matcher(" "+line.trim());
		int counter = 0;

		while (matcher.find())
			counter++;

		return counter;

	}

	static double getTFIDF(String term, String line,  InverseFreq isf){

		Pattern pattern = Pattern.compile(" "+term);
		Matcher matcher = pattern.matcher(" "+line.trim());
		int tf= 0;

		while (matcher.find())
			tf++;

		double idf = isf.getIDF(term);
		return tf * idf;
	}
}
