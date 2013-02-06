package vips;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Node;

public class VisionBlock {

    int DoC;
    int level;
    Node node;
//    IElementCollection elements;
    List<VisionBlock> children = new ArrayList<VisionBlock>();
    VisionBlock parent;
    String name = "VB";

    public int getDoC() {
        return DoC;
    }

    public void setDoC(int DoC) {
        this.DoC = DoC;
    }

    public List<VisionBlock> getChildren() {
        return children;
    }

    public VisionBlock getChild(int index) {
        return children.get(index);
    }

    public int getChildCount() {
        return children.size();
    }

    public int getIndexOfChild(VisionBlock vb) {
        return children.indexOf(vb);
    }

    public void setChildren(List<VisionBlock> children) {
        this.children = children;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public VisionBlock getParent() {
        return parent;
    }

    public void setParent(VisionBlock parent) {
        this.parent = parent;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + "(" + getDoC() + ")";
    }
}