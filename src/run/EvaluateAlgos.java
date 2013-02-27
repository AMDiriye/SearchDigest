package evaluation;

import java.util.List;

public class Evaluator {

	List<Data> testData;

	public Evaluator(List<Data> testData){
		this.testData = testData;
	}


	public static double getRecall(Data data, Data data2, List<String> labels){

<<<<<<< HEAD
		for(File file : files){
			Data _data = makeData(file.getAbsolutePath(),false);
			ArrayList<String> labels = new ArrayList<String>(){{add("aboutMe"); add("contactDetails"); add("publication"); add("researchInterests");}};
			
			for(String str : _data.getLabels()){
				if(!labels.contains(str)){
					System.out.println(str+" -- "+file.getAbsolutePath());
				}
			}
			data.add(makeData(file.getAbsolutePath(),false));
		}

		double precision = 0;
		double recall = 0;
		double totalInstances = 0;
		
		for(int i=0; i<data.size();i++){

			for(int j=i+1; j<data.size();j++){

				if(i!=j){
					System.out.println((totalInstances++)+" of "+(Math.pow(data.size(),2)-data.size())/2);
					List<String> generatedLabels = getLabelsGenerated(data.get(i),data.get(j));
					//System.out.println("--------");
					//System.out.println(data.get(i).getAllContent().substring(0,100));
					//System.out.println(data.get(j).getAllContent().substring(0,100));
					
					//System.out.println("Precision: "+Evaluator.getPrecision(data.get(i), generatedLabels) +
					//		" Recall: " +Evaluator.getRecall(data.get(i), generatedLabels));
					
					precision += Evaluator.getPrecision(data.get(i), data.get(j), generatedLabels);
					recall += Evaluator.getRecall(data.get(i), data.get(j), generatedLabels);

				}
			}
		}
		precision = (precision/totalInstances);
		recall = (recall/totalInstances);
		
		System.out.println("precision: "+precision+" -- recall: "+recall);
	}


	public static List<String> getLabelsGenerated(Data doc1, Data doc2){
		List<String> labels = new ArrayList<String>();
		
		String _doc1Terms = Utilities.stem(Utilities.removeStopWords(doc1.getAllContent()));
		String _doc2Terms = Utilities.stem(Utilities.removeStopWords(doc2.getAllContent()));
				
		Utilities.isf = new InverseDocumentFreq(_doc1Terms+ " "+_doc2Terms);
		
		for(int i=0; i<doc1.getContentSize();i++){
			
			double bestSim = 0.0;
			int closestContent = -1;
			
			for(int j=0; j<doc2.getContentSize();j++){
				String doc1Terms = Utilities.removeStopWords(doc1.getContentAt(i));
		        String doc2Terms = Utilities.removeStopWords(doc2.getContentAt(j));
		        
		        doc1Terms = Utilities.stem(doc1Terms);
		        doc2Terms = Utilities.stem(doc2Terms);
		        
		        //Change here to test other text-based metrics
				double tempSim = Utilities.cosineSimilarity(Arrays.asList(doc1Terms.split(" ")), 
						Arrays.asList(doc2Terms.split(" ")));
=======
		double numCorrect = 0;
		double numPossiblyCorrect = 0.0;
		for(int i =0; i<data.getContentSize(); i++){

			System.out.println(data.getLabels().get(i)+ " -- "+labels.get(i)+" -- "+data.getLabels().get(i).equals(labels.get(i)));
			
			if(data2.containsLabel(data.getLabels().get(i))){
				numPossiblyCorrect++;
			}
>>>>>>> Minor edits
				
			if(data.getLabels().get(i).equals(labels.get(i))){
				numCorrect++;
			}
		}

		return (numCorrect/((double)numPossiblyCorrect));
	}

	public static double getPrecision(Data data, Data data2, List<String> labels){
		double numCorrect = 0;
		double numLabels = 0;

		for(int i =0; i<data.getContentSize(); i++){
 
			if(labels.get(i) != null && data2.containsLabel(data.getLabels().get(i))){
			
				numLabels++;

				if(data.getLabels().get(i).equals(labels.get(i)))
					numCorrect++;
			}
		}
		
		if(numLabels == 0)
			return 0.0;
		
		return (numCorrect/(numLabels));
	}


	/*public double getPrecision(List<String> labelsGenerated, List<String> correctLabels){
		double numCorrect = 0;

		for(int i =0; i<labelsGenerated.size(); i++){

			if(labelsGenerated.get(i).equals(correctLabels.get(i))){
				numCorrect++;
			}
		}

		return (numCorrect/((double)labelsGenerated.size()));
	}*/


}
