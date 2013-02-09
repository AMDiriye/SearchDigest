package vips;

import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class VisionBasedPageSegmenter {

    Logger l = Logger.getRootLogger();
    int PDoC;
    BlockPool pool = new BlockPool();
    SeparatorList separatorList = null;
    BlockExtractor extractor = null;
    VBTreeConstructor constructor = new VBTreeConstructor();
    int iterateTimes = 5;
    boolean showsUp = true;

    VisionBasedPageSegmenter() {
	}
    
    public VisionBlock pageSegment(Document document) {
        init(document);
        int times = 0;
        do {
            l.debug(" ---- ---- iterate time: " + times);
            // Block Extraction.
            extractBlock();
        } while (!meetGranularityNeed() && ++times < iterateTimes);

        // Separator Detection.
        separatorDetect();
        // Content Structure Construction.
        VisionBlock blk = constructor.reconstructionVisionBlockTree(separatorList, pool);

        return blk;
    }


    private void separatorDetect() {
        /*int size = pool.getPool().size();
        for (int i = 0; i < size; i++) {
            VisionBlock block = pool.getPool().get(i);
        }

        for (int i = 0; i < size; i++) {
            VisionBlock block = pool.getPool().get(i);
            if (pool.isLeafNode(block)) {
                separatorList.evaluateBlock(block);
            }
        }

        separatorList.removeSeparatorWhichAjacentBorder();
        separatorList.expandAndRefineSeparator(pool.getLeafNodes());
        separatorList.setRelativeBlocks(pool.getLeafNodes());
        separatorList.removeSeparatorWhichNoRelativeBlocks();
        separatorList.caculateWeightOfSeparator();*/
    }

    private void extractBlock() {
        int size = pool.getPool().size();
        for (int i = 0; i < size; i++) {
            VisionBlock block = pool.getPool().get(i);
            l.trace(block.getName() + " Level: " + block.getLevel() + " DoC:" + block.getDoC());
            if (pool.isLeafNode(block) && !meetGranularityNeed(block)) {
                divideDomTree(block.getNode(), 0, block);
            }
        }
    }

    private boolean meetGranularityNeed() {
        int size = pool.getPool().size();
        for (int i = 0; i < size; i++) {
            VisionBlock block = pool.getPool().get(i);
            if (pool.isLeafNode(block) && !meetGranularityNeed(block)) {
                return false;
            }
        }
        return true;
    }

    private boolean meetGranularityNeed(VisionBlock block) {
        return block.getDoC() >= PDoC;
    }

    private void init(Document document) {
        separatorList = new SeparatorList();

        Node body = document.body();
        VisionBlock block = new VisionBlock();
        block.setNode(body);
        pool.add(block);
        Elements childFrames = document.body().getAllElements();
        if (null != childFrames) {
            for (Element doc : childFrames) {
                if (null != doc) {
                    if (NodeFeature.getInstance().isValidNode((Node)doc)) {
                        VisionBlock b = new VisionBlock();
                        b.setNode((Node)doc);
                        b.setDoC(8);
                        pool.add(b);
                    }
                }
            }
        }
    }

    private void divideDomTree(Node ele, int level, VisionBlock ancestor) {
        DivideRule divide = dividable(ele, level);
        
        if (null != divide) {
            if (BlockExtractor.Dividable == divide.dividable()) {
                List<Node> children = ele.childNodes();
                for (int i = 0; i < children.size(); i++) {
                    Node child = children.get(i);
                    divideDomTree(child, level + 1, ancestor);
                }
            } else if (BlockExtractor.UnDividable == divide.dividable()) {
                VisionBlock block = new VisionBlock();
                block.setNode(ele);
                int DoC = divide.getDoC(ele, level);
                block.setDoC(DoC);
                block.setLevel(level);
                // Add child to ancestor.
                ancestor.getChildren().add(block);
                pool.add(block);

            } else {
                // do nothing for cutting node
                //ElementUtil.getInstance().drawRectangleInPage(ele);
            }
        }
    }

    private DivideRule dividable(Node ele, int level) {
        return extractor.dividable(ele, level);
    }

    public int getPDoC() {
        return PDoC;
    }

    public void setPDoC(int PDoC) {
        this.PDoC = PDoC;
    }

    public BlockExtractor getExtractor() {
        return extractor;
    }

    public void setExtractor(BlockExtractor extractor) {
        this.extractor = extractor;
    }
}