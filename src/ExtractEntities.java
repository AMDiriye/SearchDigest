import java.util.List;

import contentalignment.Cluster;
import contentalignment.EntityExtractor;
import contentalignment.SegmentationFactory;
import contentminer.EntityFactory;
import contentminer.NamedEntity;


public class ExtractEntities {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SegmentationFactory segmentFactory = new SegmentationFactory("https://news.google.com/");
		List<NamedEntity> namedEntities = EntityFactory.generateEntities(segmentFactory.getDoc().text());
		
		
		for(NamedEntity namedEntity : namedEntities){
			System.out.println(namedEntity.getEntityValue() +" -- "+namedEntity.getType());
		}
	}

}
