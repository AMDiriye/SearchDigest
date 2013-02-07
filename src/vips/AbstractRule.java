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
        } 
        return 1;
    }

}