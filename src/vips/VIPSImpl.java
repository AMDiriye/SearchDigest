package vips;

public class VIPSImpl {

    private VisionBasedPageSegmenter segmenter = null;

    public VIPSImpl(BrowserContext context) {
        this.context = context;
        init();
    }

    public VisionBlock segment(Document doc, String url) {
        return processVIPS(doc, url);
    }

    /**
     * Creator VIPS Segmenter
     */
    private void init() {
        segmenter = new VisionBasedPageSegmenter();
        segmenter.setPDoC(BrowserContext.getConfigure().getIntProperty("VIPS", "PDoC"));
    }

    private VisionBlock processVIPS(IDocument doc, String url) {
        StyleSheet styleSheet = new StyleSheetFactory().createStyleSheet(doc);
        context.setStyleSheet(url, styleSheet);

        // Create extractor
        IElement body = doc.getBody();
        if (null != body) {
            Rectangle rect = RectangleFactory.getInstance().create(body);
            double pageSize = rect.getHeight() * rect.getWidth();
            context.getConsole().log("Page: " + rect + " Size: " + pageSize);
            double threshold = BrowserContext.getConfigure().getDoubleProperty("VIPS", "RelativeSizeThreshold", 0.1);
            BlockExtractor extractor =
                    BlockExtractorFactory.getInstance().create(context, url, standardPageSize, threshold);

            // Set extractor
            segmenter.setExtractor(extractor);
            return segmenter.pageSegment(doc);
        } else {
            l.info("This is not a HTML page, ignore... " + url);
            return null;
        }
    
    }


    public VisionBasedPageSegmenter getSegmenter() {
        return segmenter;
    }

    public void setSegmenter(VisionBasedPageSegmenter segmenter) {
        this.segmenter = segmenter;
    }
}