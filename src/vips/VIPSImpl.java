package vips;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import contentalignment.SegmentationFactory;

public class VIPSImpl {

    private VisionBasedPageSegmenter segmenter = null;

    public VIPSImpl() {
        init();
    }

    public VisionBlock segment(Document doc, String url) {
        return processVIPS(doc, url);
    }

    //TODO set PDoC
    /**
     * Creator VIPS Segmenter
     */
    private void init() {
        segmenter = new VisionBasedPageSegmenter();
        //segmenter.setPDoC(BrowserContext.getConfigure().getIntProperty("VIPS", "PDoC"));
    }

    private VisionBlock processVIPS(Document doc, String url) {
     
        // Create extractor
        Element body = doc.body();
        
        if (null != body) {
            double threshold = 1;
            BlockExtractor extractor =
                    BlockExtractorFactory.getInstance().create(url, threshold);

            // Set extractor
            segmenter.setExtractor(extractor);
            return segmenter.pageSegment(doc);
        } else {
            return null;
        }
    
    }

    public VisionBasedPageSegmenter getSegmenter() {
        return segmenter;
    }

    public void setSegmenter(VisionBasedPageSegmenter segmenter) {
        this.segmenter = segmenter;
    }
    
    
    public static void main(String args[]){
    
    	String url = "http://gigaom.com/2013/02/02/what-i-learned-at-google-and-microsoft-about-building-better-products/";
    	SegmentationFactory segmentFactory = new SegmentationFactory(url);
		
    	VIPSImpl impl = new VIPSImpl();
    	impl.segment(segmentFactory.getDoc(), url);
    }
}