package evaluation;

import java.util.List;

public class Evaluator {

	List<Data> testData;

	public Evaluator(List<Data> testData){
		this.testData = testData;
	}


	public static double getRecall(Data data, List<String> labels){

		double numCorrect = 0;

		for(int i =0; i<data.getContentSize(); i++){

			if(data.getLabels().get(i).equals(labels.get(i))){
				numCorrect++;
			}
		}

		return (numCorrect/((double)labels.size()));
	}

	public static double getPrecision(Data data, List<String> labels){
		double numCorrect = 0;
		double numLabels = 0;

		for(int i =0; i<data.getContentSize(); i++){

			if(labels.get(i) != null){
			
				numLabels++;

				if(data.getLabels().get(i).equals(labels.get(i)))
					numCorrect++;
			}
		}
		
		if(numLabels == 0)
			return 0.0;
		
		return (numCorrect/(numLabels));
	}


	public double getPrecision(List<String> labelsGenerated, List<String> correctLabels){
		double numCorrect = 0;

		for(int i =0; i<labelsGenerated.size(); i++){

			if(labelsGenerated.get(i).equals(correctLabels.get(i))){
				numCorrect++;
			}
		}

		return (numCorrect/((double)labelsGenerated.size()));
	}


}
