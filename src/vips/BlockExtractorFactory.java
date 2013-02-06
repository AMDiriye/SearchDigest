package vips;

public class BlockExtractorFactory {

    private final static BlockExtractorFactory instance = new BlockExtractorFactory();

    public static BlockExtractorFactory getInstance() {
        return instance;
    }

    public BlockExtractor create(BrowserContext context, String referrer, double pageSize, double threshold) {
        VipsBlockExtractor extractor = new VipsBlockExtractor();
        extractor.setPageSize(pageSize);
        extractor.setContext(context);
        extractor.setReferrer(referrer);
        extractor.setThreshold(threshold);
        extractor.setConfigure(BrowserContext.getConfigure());
        return extractor;
    }
}
