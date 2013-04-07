package similarityalgos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utilities.Utilities;

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
	
	public static void main(String args[]){
		CharacterBasedDistribution doc1 = new CharacterBasedDistribution("M. Dšrk, N.H. Riche, G. Ramos and S.T. Dumais.  PivotPaths: Strolling through faceted information spaces.  To appear in Proceedings of InfoVis 2012. A. Diriye, R.W. White, G. Buscher and S.T. Dumais.  Leaving so soon? Understanding and predicting web search abandonment rationales.  To appear in Proceedings of CIKM 2012. S.T. Dumais (2012).  Putting search into context and context into search.  SIGIR 2012, Industry Keynote. P.N. Bennett, R.W. White, W. Chu, S.T. Dumais, P. Bailey, F. Borisyuk and X. Cui.  Modeling the impact of short- and long-term behavior on search personalization.  In Proceedings of SIGIR 2012. K. Svore, J. Teevan, S.T. Dumais and A. Kulkarni.  Creating temporally dynamic web search snippets.  In Proceedings of SIGIR 2012 (Poster). E. Agichtein, R.W. White, S.T. Dumais and P.N. Bennett.  Search interrupted: Understanding and predicting search task continuation.  In Proceedings of SIGIR 2012. S.T. Dumais (2012).  Data-driven approaches to improving information access.  Festschrift for Richard M. Shiffrin. S.T. Dumais (2012).  Temporal dynamics and information retrieval.  SIAM-SDM 2012, Keynote Talk. M. Bernstein, J. Teevan and S.T. Dumais (2012).  Direct answers for search queries in the long tail.  In Proceedings of CHI 2012.  [Best Paper, Honorable Mention] K. Radinski, K. Svore, S.T. Dumais, J. Teevan, E. Horvitz and A. Bocharov (2012).  Modeling and predicting behavioral dynamics on the Web.  In Proceedings of WWW 2012. J. Kim, K. Collins-Thompson, P. Bennett and S. T. Dumais (2012).  Characterizing web content, user interests and searching behavior by reading level and topic. In Proceedings of WSDM 2012. G. Buscher, R. White, S. T. Dumais and J. Huang (2012).  Large-scale analysis of individual and task differences on search result page examination strategies. In Proceedings of WSDM 2012. D. Sontag, K. Collins-Thompson, P. Bennett, R. White and S. T. Dumais (2012).  Probabilistic models for personalizing web search. In Proceedings of WSDM 2012. D. Ramage, C. Manning and S. T. Dumais (2011).  Partially labeled topic models for interpretable text mining.  In Proceedings of KDD 2011. A. Kotov, P. Bennett, R. White, S. T. Dumais and J. Teevan (2011).  Modeling and analyses of multi-session search tasks.   In Proceedings of SIGIR 2011. Q. Guo, R. White and S. T. Dumais (2011).  Why users switch: Understanding and predicting search engine switching rationales.   In Proceedings of SIGIR 2011. S.T. Dumais (2011).  Temporal dynamics and information systems.  (Slides.)  iConference 2011, Keynote Talk. S.T. Dumais, Jeffries, R., Russell, D., Tang, D. and Teevan, J. (2011).  Design of large scale log analysis studies.  In Proceedings of CHI 2011 (Course). Huang, J., White, R. and Dumais, S.T. (2011).  No clicks no problems: Using cursor movements to understand and improve search. In Proceedings of CHI 2011.   [Best Paper, Honorable Mention] C. Danescu-Niculescu-Mizil, M. Gamon and Dumais, S.T. (2011).  Mark my words: Linguistic accommodation in social media.  In Proceedings of WWW 2011. A. Kulkarni, A., Teevan, J., Svore, K. and Dumais, S.T. (2011).  Understanding temporal query dynamics.  In Proceedings of WSDM 2011. S.T. Dumais (2010).  Temporal dynamics and information retrieval.  CIKM 2010, Keynote Talk.");
		CharacterBasedDistribution doc2 = new CharacterBasedDistribution("Mining and Using Contextual Information from Large-Scale Web Search Logs. Invited talk at Emory University (Nov 2012). Also presented at Georgia Institute of Technology. P.N. Bennett, R.W. White, W. Chu, S.T. Dumais, P. Bailey, F. Borisyuk, and X. Cui (2012).  Modeling the Impact of Short- and Long-Term Behavior on Search Personalization.   In Proceedings of the 35th Annual ACM SIGIR Conference (SIGIR 2012).  Portland, WA.  August 2012. L. Wang, P.N.Bennett, K. Collins-Thompson (2012).  Robust Ranking Models via Risk-Sensitive Optimization.   In Proceedings of the 35th Annual ACM SIGIR Conference (SIGIR 2012).  Portland, WA.  August 2012. (Honorable Mention Best Paper) David Sontag, Kevyn Collins-Thompson, Paul N. Bennett, Ryen White, Susan Dumais, and Bodo von Billerbeck (2012).   Probabilistic Models for Personalizing Web Search.  In Proceedings of the 5th ACM International Conference on Web Search and Data Mining (WSDM '12).  Seattle, Washington.  February 2012. R. White, P.N. Bennett, S. Dumais (2010).   Predicting Short-term Interests Using Activity-Based Search Contexts.  In Proceedings of the 19th ACM International Conference on Information and Knowledge Management (CIKM '10).  Toronto, Canada.  Oct 2010. Contextual Signals Kevyn Collins-Thompson, Paul N. Bennett, Ryen White, Sebastian de la Chica, and David Sontag (2011).   Personalizing Web Search Results by Reading Level.  In Proceedings of the 20th ACM International Conference on Information and Knowledge Management (CIKM '11).  Glasgow, Scotland.  October 2011. Paul N. Bennett, Filip Radlinski, Ryen W. White, and Emine Yilmaz (2011).   Inferring and Using Location Metadata to Personalize Web Search.  In Proceedings of the 34th Annual International ACM SIGIR Conference on Research and Development in Information Retrieval (SIGIR 2011).  Beijing, China.  July 2011. Paul N. Bennett, Khalid El-Arini, Thorsten Joachims, Krysta M. Svore (2011).   Enriching Information Retrieval (Summary of the SIGIR 2011 Workshop).  In SIGIR Forum. pp. 60-65. Dec 2011. Class-Based Contextualized Search. Invited talk at Carnegie Mellon, Language Technologies Institute Colloquium (Oct 2010). Talk abstract. Also, presented at the University of Texas at Austin, Purdue University, and UIUC. PDF of recent slides are here. P.N. Bennett, K. Svore, S. Dumais (2010).  Classification-Enhanced Ranking.  In Proceedings of the 19th Annual International World Wide Web Conference (WWW '10).  Raleigh, NC.  April 2010.");
		CharacterBasedDistribution doc3 = new CharacterBasedDistribution("I am interested in the development, improvement, and analysis of machine learning methods with a focus on systems that can aid in the automatic analysis of natural language as components of adaptive systems or information retrieval systems. My current focus is on contextual and personalized search. I am also actively engaged in enriched information retrieval, active sampling and learning, hierarchical and large-scale classification, and human computation and preferences. My past work has examined a variety of areas Ñ primarily ensemble methods, calibrating classifiers, search query classification and characterization, and redundancy and diversity, but also extending to transfer learning, machine translation, recommender systems, and knowledge bases. In addition to my research, I engage in a variety of professional service activities for the machine learning, data mining, and information retrieval communities.");
		CharacterBasedDistribution doc4 = new CharacterBasedDistribution("I am interested in algorithms and interfaces for improved information retrieval, as well as general issues in human-computer interaction. I joined Microsoft Research in July 1997. I work on a wide variety of information access and management issues, including: personal information management, web search, question answering, information retrieval, text categorization, collaborative filtering, interfaces for improved search and navigation, and user/task modeling.");
		System.out.println(Utilities.jsDivergence(doc1.distribution,doc3.distribution));
		System.out.println(Utilities.jsDivergence(doc1.distribution,doc2.distribution));
		System.out.println(Utilities.jsDivergence(doc4.distribution,doc1.distribution));
		System.out.println(Utilities.jsDivergence(doc2.distribution,doc3.distribution));
		System.out.println(Utilities.jsDivergence(doc2.distribution,doc4.distribution));
		System.out.println(Utilities.jsDivergence(doc3.distribution,doc4.distribution));
	
	}
}