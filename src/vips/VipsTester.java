/*
 * Tomas Popela, 2012
 * VIPS - Visual Internet Page Segmentation
 * Module - VipsTester.java
 */

package vips;

/**
 * VIPS API example application.
 * @author Tomas Popela
 *
 */
public class VipsTester {

	/**
	 * Main function
	 * @param args Internet address of web page.
	 */
	public static void main(String args[])
	{
 

		String url = "http://research.microsoft.com/en-us/um/people/ryenw/index.html";

		try
		{
			Vips vips = new Vips();
			// disable graphics output
			vips.enableGraphicsOutput(false);
			// disable output to separate folder (no necessary, it's default value is false)
			vips.enableOutputToFolder(false);
			// set permitted degree of coherence
			vips.setPredefinedDoC(8);
			// start segmentation on page
			vips.startSegmentation(url);
			
			VisualStructureConstructor vsc = vips.getVisualStructureConstructor();

			for(VipsBlock vb : vsc.getVisualBlocks()){
				
				System.out.println("---------------");
				System.out.println(vb.getSourceIndex()+" - "+vb.getDoC()+" - "+vb.getBox().getText());
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
