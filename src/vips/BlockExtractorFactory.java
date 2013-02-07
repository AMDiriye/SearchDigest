package vips;

public class BlockExtractorFactory {

	private final static BlockExtractorFactory instance = new BlockExtractorFactory();

	public static BlockExtractorFactory getInstance() {
		return instance;
	}

	public BlockExtractor create( String referrer, double threshold) {
		VipsBlockExtractor extractor = new VipsBlockExtractor();
		extractor.setReferrer(referrer);
		extractor.setThreshold(threshold);
		return extractor;
	}
}
