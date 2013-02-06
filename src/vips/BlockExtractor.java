package vips;

public interface BlockExtractor {

    public final static int Dividable = -1;
    public final static int UnDividable = -2;
    public final static int Cut = -3;

    public DivideRule dividable(IElement ele, int level);
}
