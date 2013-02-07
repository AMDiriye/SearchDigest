package vips;

import org.jsoup.nodes.Node;

public interface BlockExtractor {

    public final static int Dividable = -1;
    public final static int UnDividable = -2;
    public final static int Cut = -3;

    public DivideRule dividable(Node node, int level);
}
