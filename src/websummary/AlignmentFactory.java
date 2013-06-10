package websummary;

import java.util.ArrayList;
import java.util.List;

import contentalignment.Cluster;
import contentalignment.PageSegmentGrouper;
import contentalignment.SegmentationFactory;
import document.WebPage;
import document.WebPageSection;

public class AlignmentFactory {

	List<Cluster> alignedWebPages;
	String url;
	List<List<WebPageSection>> segmentedWebPages;
	
	public AlignmentFactory(WebPage[] webPages){
		
		if(webPages.length == 1)
			this.url = webPages[0].getURL();
		
		alignWebPages(webPages);
	}

	private void alignWebPages(WebPage[] webPages){

		segmentedWebPages = new ArrayList<List<WebPageSection>>();

		for(WebPage webPage : webPages){
			SegmentationFactory segmentFactory = new SegmentationFactory(webPage.getDoc());
			PageSegmentGrouper pageSegmentGrouper = new PageSegmentGrouper(segmentFactory.getSegments(), segmentFactory.getDoc());
			List<WebPageSection> segmentedWebPage = pageSegmentGrouper.getCluster();
			webPage.setWebPageSegments(segmentedWebPage);
			segmentedWebPages.add(segmentedWebPage);
		}	
		alignedWebPages = alignDocs(segmentedWebPages);
	}

	//Aligns webpages of webpagesections together
	private List<Cluster> alignDocs(List<List<WebPageSection>> docs){

		List<Cluster> alignedContent = new ArrayList<Cluster>();
		
		//iterates through each doc
		for(int i=0; i< docs.size(); i++){
			System.out.println("Processing "+(i+1)+" of "+docs.size());
			List<WebPageSection> doc = docs.get(i);
			List<Integer> rowsUnavailable = new ArrayList<Integer>();

			//find best matching if none add new row
			for(int j=0; j<doc.size(); j++){

				WebPageSection segment = doc.get(j);

				if(i == 0){
					
					Cluster tempCluster = new Cluster();
					tempCluster.addWebPageSection(0, segment);
					alignedContent.add(tempCluster);
					
				}
				else{

					int bestCluster = findBestCluster(segment, alignedContent,rowsUnavailable);

					if(bestCluster == -1){
						Cluster tempCluster = new Cluster();
						tempCluster.addWebPageSection(0, segment);
						alignedContent.add(tempCluster);
						rowsUnavailable.add(new Integer(alignedContent.size()-1));
					}
					else{
						rowsUnavailable.add(new Integer(bestCluster));
						alignedContent.get(bestCluster).setSegmentAlignedFlag();
						alignedContent.get(bestCluster).addWebPageSection(i, segment);
					}
				}
			}
		}
		return alignedContent;
	}

	private int findBestCluster(WebPageSection cluster, List<Cluster> clusters, List<Integer> rowsUnavailable) {
		double bestSimVal = Double.NEGATIVE_INFINITY;
		int posBestCluster = -1;

		for(int i = 0;i < clusters.size(); i++){

			double tempSimVal = clusters.get(i).getSimilarity(cluster);
			if(tempSimVal > bestSimVal && !rowsUnavailable.contains(new Integer(i)) && tempSimVal > 0.25){
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
