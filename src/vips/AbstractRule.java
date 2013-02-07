package vips;

import org.jsoup.nodes.Node;

public abstract class AbstractRule implements DivideRule {

    NodeFeature nodeFeature = NodeFeature.getInstance();

    @Override
    public int dividable() {
        return BlockExtractor.Dividable;
    }

    @Override
    public int getDoC(Node ele, int level) {
        if (BlockExtractor.Dividable == dividable()
                || BlockExtractor.Cut == dividable()) {
            return 0;
        } else {
            return getDoCBasedTagAndSize(ele, level);
        }
    }

    private int getDoCBasedTagAndSize(Node ele, int level) {
        int DoC = 0;

        boolean hasSmallChildren = nodeFeature.areChildrenSmallNode(ele);
        if (hasSmallChildren) {
            DoC += 2;
        }
        if (nodeFeature.hasLineBreakChildNode(ele)) {
            DoC -= 1;
        }
        return DoC;
    }
}