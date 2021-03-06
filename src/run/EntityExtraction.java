package run;
import java.util.List;

import namedentities.EntityFactory;
import namedentities.NamedEntity;

import contentalignment.Cluster;
import contentalignment.PageSegmentGrouper;
import contentalignment.SegmentationFactory;


public class EntityExtraction {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		args = "http://www.wikihow.com/Unclog-a-Bathtub-Drain".split("[,]");
		SegmentationFactory segmentFactory = new SegmentationFactory(args[0]);
		List<NamedEntity> namedEntities = EntityFactory.generateEntities(segmentFactory.getDoc().text());
		
		
		for(NamedEntity namedEntity : namedEntities){
			System.out.println(namedEntity.getEntityValue() +" -- "+namedEntity.getType());
		}
	}

}
