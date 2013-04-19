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
		args = "http://www.amazon.com/Nikon-COOLPIX-Digital-Camera-NIKKOR/dp/B0073HSJV0/ref=sr_1_13?ie=UTF8&qid=1359990405&sr=8-13&keywords=camera,http://www.amazon.com/GE-X500-BK-Optical-Digital-Camera/dp/B004LB4SAM/ref=sr_1_15?ie=UTF8&qid=1359990405&sr=8-15&keywords=camera".split("[,]");
		SegmentationFactory segmentFactory = new SegmentationFactory(args[0]);
		List<NamedEntity> namedEntities = EntityFactory.generateEntities(segmentFactory.getDoc().text());
		
		
		for(NamedEntity namedEntity : namedEntities){
			System.out.println(namedEntity.getEntityValue() +" -- "+namedEntity.getType());
		}
	}

}
