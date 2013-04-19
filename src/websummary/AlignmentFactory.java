package websummary;

import java.util.ArrayList;
import java.util.List;

import contentalignment.Cluster;
import contentalignment.PageSegmentGrouper;
import contentalignment.SegmentationFactory;
import document.WebPage;

public class AlignmentFactory {

	List<Cluster> alignedWebPages;
	
	public AlignmentFactory(WebPage[] webPages){
		alignWebPages(webPages);
	}
	
	private void alignWebPages(WebPage[] webPages){
		
		List<List<Cluster>> segmentedWebPages = new ArrayList<List<Cluster>>();
		
		for(WebPage webPage : webPages){
			SegmentationFactory segmentFactory = new SegmentationFactory(webPage.getDoc());
			PageSegmentGrouper pageSegmentGrouper = new PageSegmentGrouper(segmentFactory.getSegments(), segmentFactory.getDoc());
			List<Cluster> segmentedWebPage = pageSegmentGrouper.getCluster();
			segmentedWebPages.add(segmentedWebPage);
		}	
		alignedWebPages = alignDocs(segmentedWebPages);
	}


	private List<Cluster> alignDocs(List<List<Cluster>> docs){

		List<Cluster> alignedContent = new ArrayList<Cluster>();

		//iterates through each doc
		for(int i=0; i< docs.size(); i++){
			List<Cluster> doc = docs.get(i);
			List<Integer> rowsUnavailable = new ArrayList<Integer>();

			//find best matching if none add new row
			for(int j=0; j<doc.size(); j++){

				Cluster segment = doc.get(j);

				if(i == 0){
					Cluster tempCluster = new Cluster();
					tempCluster.addCluster(0, segment);
					alignedContent.add(tempCluster);

				}
				else{

					int bestCluster = findBestCluster(segment, alignedContent,rowsUnavailable);

					if(bestCluster == -1){
						Cluster tempCluster = new Cluster();
						tempCluster.addCluster(i, segment);
						alignedContent.add(tempCluster);
						rowsUnavailable.add(new Integer(alignedContent.size()-1));
					}
					else{
						rowsUnavailable.add(new Integer(bestCluster));
						alignedContent.get(bestCluster).addCluster(i, segment);					
					}
				}
			}
		}
		return alignedContent;
	}

	private int findBestCluster(Cluster cluster, List<Cluster> clusters, List<Integer> rowsUnavailable) {
		double bestSimVal = 0;
		int posBestCluster = -1;

		for(int i = 0;i < clusters.size(); i++){

			double tempSimVal = clusters.get(i).getSimilarity(cluster);

			System.out.println(tempSimVal);

			if(tempSimVal > bestSimVal && !rowsUnavailable.contains(new Integer(i))){
				bestSimVal = tempSimVal;
				posBestCluster = i;
			}
		}
		return posBestCluster;
	}

	public List<Cluster> getAlignedWebPages(){
		return alignedWebPages;
	}

}
