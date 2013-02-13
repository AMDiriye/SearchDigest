package contentsegmentation;

import org.fit.cssbox.layout.Box;
import org.fit.cssbox.layout.ElementBox;
import org.fit.cssbox.layout.TextBox;
import org.fit.cssbox.layout.Viewport;

import vips.VipsBlock;


public class WebBlockExtractor {
	
	private WebBlock webBlocks = null;
	
	private int widthThreshold = 0;
	private int heighThreshold = 0;
	private Viewport viewport = null;
	private int numWebBlock= 0;
	private int pageWidth = 0;
	private int pageHeight = 0;
	
	
	public WebBlockExtractor(Viewport viewport) {
		this.viewport = viewport;
		this.webBlocks = new WebBlock();
		this.heighThreshold = 80;
		this.widthThreshold = 80;
		this.pageWidth = viewport.getWidth();
		this.pageHeight = viewport.getHeight();
	}

	public WebBlockExtractor(Viewport viewport, int widthThreshold, int heighThreshold) {
		this.viewport = viewport;
		this.webBlocks = new WebBlock();
		this.widthThreshold = widthThreshold;
		this.heighThreshold = heighThreshold;
	}

	
	public void extract(){
		
		if (viewport != null){
			this.webBlocks = new WebBlock();
			numWebBlock = 0;

			makeWebBlocks(viewport.getElementBoxByName("body", false), webBlocks);

		}
		else
			System.err.print("Page's viewPort is not defined");
	}

	private void makeWebBlocks(Box box, WebBlock webBlocks) {
		webBlocks.setBox(box);

		if (! (box instanceof TextBox)){
			
			for (Box subBox: ((ElementBox) box).getSubBoxList()){
				webBlocks.addChild(new webBlock());
				makeWebBlocks(subBox, webBlocks.getChildren().get(webBlocks.getChildren().size()-1));
			}
		}
	}
}
