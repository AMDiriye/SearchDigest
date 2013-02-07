package vips;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Separator {

    int weight = 0;
    Set<VisionBlock> relativeBlocks = new HashSet<VisionBlock>();
    Separator parent;
    List<Separator> children = new ArrayList<Separator>();



    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Set<VisionBlock> getRelativeBlocks() {
        return relativeBlocks;
    }

    public void setRelativeBlocks(Set<VisionBlock> relativeBlocks) {
        this.relativeBlocks = relativeBlocks;
    }

    public List<Separator> getChildren() {
        return children;
    }

    public void setChildren(List<Separator> children) {
        this.children = children;
    }

    public Separator getParent() {
        return parent;
    }

    public void setParent(Separator parent) {
        this.parent = parent;
    }

    //TODO compute top block
 

}