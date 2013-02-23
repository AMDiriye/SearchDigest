package evaluation;

import java.util.List;

public class Evaluator {

	List<Data> testData;
	
	public Evaluator(List<Data> testData){
		this.testData = testData;
	}
	
	
	public double getPrecision(Data data, List<String> labels){
		double numCorrect = 0;
		
		for(int i =0; i<data.getContentSize(); i++){
			
			if(data.getLabels().get(i).equals(labels.get(i))){
				numCorrect++;
			}
		}
		
		return (numCorrect/((double)labels.size()));
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
