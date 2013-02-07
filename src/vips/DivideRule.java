package vips;
import org.jsoup.nodes.Node;

public interface DivideRule {

    /**
     * If DOM node match divide rule, the return true.<br/>
     * @param ele
     * @param level
     * @return
     */
    public boolean match(Node ele, int level);

    /**
     * Determine whether divide the node if matched this rule.
     * @return
     */
    public int dividable();

    /**
     * Compute the Degree of Coherence of this node.
     * @param ele
     * @param level
     * @return
     */
    public int getDoC(Node ele, int level);

}