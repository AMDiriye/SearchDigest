package vips;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

public class SeparatorList {

    Logger log = Logger.getRootLogger();
    List<Separator> list = new CopyOnWriteArrayList<Separator>();

    public SeparatorList() {
        Separator sep = new Separator();
        add(sep);
    }

    public final void add(Separator sep) {
        list.add(sep);
    }

    public void expandAndRefineSeparator(List<VisionBlock> blocks) {
        refineSeparator(blocks);
    }


    private void refineSeparator(List<VisionBlock> blocks) {
        for (VisionBlock block : blocks) {
            //evaluateBlock(block);
        }
    }

    public void removeSeparatorWhichNoRelativeBlocks() {
        for (Iterator<Separator> it = list.iterator(); it.hasNext();) {
            Separator sep = it.next();
            if (sep.getRelativeBlocks().isEmpty()) {
                list.remove(sep);
            }
        }
    }

    public void caculateWeightOfSeparator() {
        for (Iterator<Separator> it = list.iterator(); it.hasNext();) {
            Separator sep = it.next();
            //caculateWeightOfSeparator(sep);
        }
    }

	public List<Separator> getSortedList() {
		// TODO Auto-generated method stub
		return list;
	}


}