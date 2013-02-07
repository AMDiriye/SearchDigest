package vips;

import java.util.*;
import org.apache.log4j.Logger;

/**
 *
 * @author ray
 */
public class VBTreeConstructor {

    Logger l = Logger.getRootLogger();
    Map<String, Separator> repos = new HashMap<String, Separator>();

    public VisionBlock reconstructionVisionBlockTree(SeparatorList sepList, BlockPool pool) {
        VisionBlock root = copyBlock(pool.getRoot());
        List<Separator> sortedList = sepList.getSortedList();

        for (int i = 0; i < sortedList.size(); i++) {
            merge(sortedList, i);
        }

        for (int i = 0; i < sortedList.size(); i++) {
            Separator sep = sortedList.get(i);
            Set<VisionBlock> blocks = sep.getRelativeBlocks();
            VisionBlock blk = combine(blocks);
            if (null != sep.getParent()) {
                Separator parent = sep.getParent();
                parent.getRelativeBlocks().add(blk);
            } else {
                root.getChildren().add(blk);
            }
        }

        addTextBlockIntoVBTree(pool);

        setVisionBlockName(root, root.getName());

        return root;
    }

    private void setVisionBlockName(VisionBlock block, String name) {
        if (block.getChildCount() > 0) {
            List<VisionBlock> children = block.getChildren();
            for (int i = 0; i < children.size(); i++) {
                VisionBlock child = children.get(i);
                child.setName(name + "_" + i);
                setVisionBlockName(child, child.getName());
            }
        } else {
//            block.setName(name + "_" + index);
        }
    }

    private VisionBlock combine(Set<VisionBlock> blocks) {
        VisionBlock blk = new VisionBlock();
        if (blocks.size() > 1) {
            for (VisionBlock block : blocks) {
                blk.getChildren().add(block);
            }
        } else {
            blk = blocks.toArray(new VisionBlock[0])[0];
        }
        return blk;
    }

    private void merge(List<Separator> sortedSepList, int start) {
        Separator sep = sortedSepList.get(start);
        findParentAndAddAsChild(sep, sortedSepList.subList(start + 1, sortedSepList.size()));
    }

    //TODO fill out method
    private void findParentAndAddAsChild(Separator sep, List<Separator> subList) {
        int d = Integer.MAX_VALUE;

    }

    public void addTextBlockIntoVBTree(BlockPool pool) {
    }

    private VisionBlock copyBlock(VisionBlock blk) {
        if (null != blk) {
            VisionBlock block = new VisionBlock();
            block.setLevel(blk.getLevel());
            block.setNode(blk.getNode());
            block.setDoC(blk.getDoC());
            return block;
        } else {
            return null;
        }
    }
}