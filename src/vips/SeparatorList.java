package vips;

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
        removeSeparatorWhichAjacentBorder();
    }


    private void refineSeparator(List<VisionBlock> blocks) {
        for (VisionBlock block : blocks) {
            evaluateBlock(block);
        }
    }

    public void removeSeparatorWhichAjacentBorder() {
        for (Iterator<Separator> it = list.iterator(); it.hasNext();) {
            Separator sep = it.next();
            Rectangle rect = sep.getRect();
            if ((rect.getLeft() == 0 && rect.getHeight() > rect.getWidth())
                    || (rect.getTop() == 0) && rect.getWidth() > rect.getHeight()) {
                list.remove(sep);
            } else if (((rect.getLeft() + rect.getWidth()) == page.getWidth() && rect.getHeight() > rect.getWidth())
                    || (rect.getTop() + rect.getHeight()) == page.getHeight() && rect.getWidth() > rect.getHeight()) {
                list.remove(sep);
            }
        }
    }

    public void removeSeparatorWhichNoRelativeBlocks() {
        for (Iterator<Separator> it = list.iterator(); it.hasNext();) {
            Separator sep = it.next();
            if (sep.getRelativeBlocks().isEmpty()) {
                log.debug("remove separator which relative block is empty, and rectangle is " + sep.getRect());
                list.remove(sep);
            }
        }
    }

    public void setRelativeBlocks(List<VisionBlock> blocks) {
        for (VisionBlock block : blocks) {
            Rectangle rect = block.getRect();
            if (null != rect) {
                boolean isVertical = rect.getHeight() > rect.getWidth();
                Separator properSep = null;
                int d = Integer.MAX_VALUE;
                for (Iterator<Separator> it = list.iterator(); it.hasNext();) {
                    Separator sep = it.next();
                    if (isVertical && sep.isVertical()) {
                        if (isAjacentVertical(rect, sep.getRect())) {
                            properSep = sep;
                            break;
                        } else {
                            continue;
                        }
                    } else if (!isVertical && !sep.isVertical()) {
                        if (isAjacentHorizontal(rect, sep.getRect())) {
                            properSep = sep;
                            break;
                        }
                    } else if (isVertical && !sep.isVertical()) {
                        if (isAjacentHorizontal(rect, sep.getRect())) {
                            properSep = sep;
                            d = 0;
                        }
                    }
                    int d1 = getHorizontalDistance(rect, sep.getRect());
                    if (d1 < d) {
                        d = d1;
                        properSep = sep;
                    }
                }
                if (null != properSep) {
                    properSep.getRelativeBlocks().add(block);
                } else {
                    log.debug("can not find proper separator.\n" + rect);
                }
            }
        }
    }

    /**
     * hwlt(100,2,0,0) isAjacentVertical with hwlt(90,50,3,0)<br/>
     * hwlt = HeighWidthLeftTop
     * @param r
     * @param sep
     * @return
     */
    private boolean isAjacentVertical(Rectangle r, Rectangle sep) {
        int x1 = r.getLeft();
        int y1 = r.getTop();
        int x2 = x1 + r.getWidth();
        int y3 = y1 + r.getHeight();

        int _x1 = sep.getLeft();
        int _y1 = sep.getTop();
        int _x2 = _x1 + sep.getWidth();
        int _y3 = _y1 + sep.getHeight();

        if (_y1 <= y1 && y3 <= _y3) {
            int d = x1 - _x2;
            if (d >= 0 && d < 5) {
                return true;
            } else {
                d = _x1 - x2;
                if (d >= 0 && d < 5) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * hwlt(2,200,0,100) isAjacentHorizontal with hwlt(50,100,0,50)<br/>
     * hwlt = HeighWidthLeftTop
     * @param r
     * @param sep
     * @return
     */
    private boolean isAjacentHorizontal(Rectangle r, Rectangle sep) {
        int x1 = r.getLeft();
        int y1 = r.getTop();
        int x2 = x1 + r.getWidth();
        int y3 = y1 + r.getHeight();

        int _x1 = sep.getLeft();
        int _y1 = sep.getTop();
        int _x2 = _x1 + sep.getWidth();
        int _y3 = _y1 + sep.getHeight();

        if (_x1 <= x1 && x2 <= _x2) {
            int d = y1 - _y3;
            if (d >= 0 && d < 5) {
                return true;
            } else {
                d = _y1 - y3;
                if (d >= 0 && d < 5) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 2 = getHorizontalDistance(hwlt(50,100,0,48), hwlt(2,200,0,100))<br/>
     * hwlt = HeighWidthLeftTop
     * @param r
     * @param sep
     * @return
     */
    private int getHorizontalDistance(Rectangle r, Rectangle sep) {
        int x1 = r.getLeft();
        int y1 = r.getTop();
        int x2 = x1 + r.getWidth();
        int y3 = y1 + r.getHeight();

        int _x1 = sep.getLeft();
        int _y1 = sep.getTop();
        int _x2 = _x1 + sep.getWidth();
        int _y3 = _y1 + sep.getHeight();

        if (_x1 <= x1 && x2 <= _x2) {
            int d = y1 - _y3;
            if (d >= 0 && d < 5) {
                return d;
            } else {
                d = _y1 - y3;
                if (d >= 0 && d < 5) {
                    return d;
                }
            }
        }

        return Integer.MAX_VALUE;
    }

    public void caculateWeightOfSeparator() {
        for (Iterator<Separator> it = list.iterator(); it.hasNext();) {
            Separator sep = it.next();
            caculateWeightOfSeparator(sep);
        }
    }

    public List<Separator> getSortedList() {
        List<Separator> l = new ArrayList<Separator>(list);
        Collections.sort(l, new Comparator<Separator>() {

            @Override
            public int compare(Separator o1, Separator o2) {
                return o1.getWeight() - o2.getWeight();
            }
        });
        return l;
    }

    /**
     * For every block in the pool, the relation of the block with each separator is evaluated.
     * @param block
     */
    public void evaluateBlock(VisionBlock block) {
        if (null != block.getRect() && !block.getEle().isTextNode()) {
            for (Iterator<Separator> it = list.iterator(); it.hasNext();) {
                Separator sep = it.next();
                if (doesSeparatorContainBlock(block, sep)) {
                    // If the block is contained in the separator, split the separator.
                    log.debug("SeparatorContainBlock");
                    Separator newSep = splitWhileContained(sep.getRect(), block.getRect());
//                    newSep.getRelativeBlocks().add(block);
//                    sep.getRelativeBlocks().add(block);
                } else if (doesBlockCoverSeparator(block, sep)) {
                    // If the block covers the separator, remove the separator.
                    log.debug("BlockCoverSeparator");
                    list.remove(sep);
                } else if (doesBlockAcrossSeparator(block, sep)) {
                    // If block across separator, then split separator.
                    log.debug("BlockAcrossSeparator");
                    Separator newSep = split(sep.getRect(), block.getRect());
//                    newSep.getRelativeBlocks().add(block);
//                    sep.getRelativeBlocks().add(block);
                } else {
                    // If the block crosses with the separator, update the separator's parameters.
                    log.debug("UpdateWhileBlockCrossedBorder");
                    doUpdateWhileBlockCrossedBorder(block.getRect(), sep.getRect());
//                    sep.getRelativeBlocks().add(block);
                }
//                caculateWeightOfSeparator(sep);
            }
        } else {
            String xpath = XPathAttrFactory.getInstance().create(block.getEle());
            log.debug(xpath + "  ---  is not leaf node.");
        }
    }

    /**
     * Split rect1 with rect2<br/>
     * @param rect1
     * @param rect2
     * @return
     */
    private Separator split(Rectangle rect1, Rectangle rect2) {
        if (rect1.getLeft() < rect2.getLeft()) {
            // split vertical
            int h = rect1.getHeight();
            int w = rect1.getWidth() - rect2.getWidth() - (rect2.getLeft() - rect1.getLeft());
            int l = rect2.getLeft() + rect2.getWidth();
            int t = rect1.getTop();
            Separator sep = new Separator();
            sep.setRect(new Rectangle(h, w, l, t));
            caculateWeightOfSeparator(sep);
            add(sep);
            rect1.setWidth(rect2.getLeft() - rect1.getLeft());
            return sep;
        } else {
            // split horizontal
            int h = rect1.getHeight() - rect2.getHeight() - (rect2.getTop() - rect1.getTop());
            int w = rect1.getWidth();
            int l = rect1.getLeft();
            int t = rect2.getTop() + rect2.getHeight();
            Separator sep = new Separator();
            sep.setRect(new Rectangle(h, w, l, t));
            caculateWeightOfSeparator(sep);
            add(sep);
            rect1.setHeight(rect2.getTop() - rect1.getTop());
            return sep;
        }
    }

    /**
     * Split rect1 with rect2
     * @param rect1
     * @param rect2
     * @return 
     */
    private Separator splitWhileContained(Rectangle rect1, Rectangle rect2) {
        if (rect2.getHeight() > rect2.getWidth()) {
            // split vertical
            int h = rect1.getHeight();
            int w = rect1.getWidth() - rect2.getWidth() - (rect2.getLeft() - rect1.getLeft());
            int l = rect2.getLeft() + rect2.getWidth();
            int t = rect1.getTop();
            Separator sep = new Separator();
            sep.setRect(new Rectangle(h, w, l, t));
            caculateWeightOfSeparator(sep);
            add(sep);
            rect1.setWidth(rect2.getLeft() - rect1.getLeft());
            return sep;
        } else {
            // split horizontal
            int h = rect1.getHeight() - rect2.getHeight() - (rect2.getTop() - rect1.getTop());
            int w = rect1.getWidth();
            int l = rect1.getLeft();
            int t = rect2.getTop() + rect2.getHeight();
            Separator sep = new Separator();
            sep.setRect(new Rectangle(h, w, l, t));
            caculateWeightOfSeparator(sep);
            add(sep);
            rect1.setHeight(rect2.getTop() - rect1.getTop());
            return sep;
        }
    }

    /**
     * Return true if separator contains block.
     * @param block
     * @param sep
     * @return
     */
    private boolean doesSeparatorContainBlock(VisionBlock block, Separator sep) {
        return isContained(block.getRect(), sep.getRect());
    }

    /**
     * Return true if separator contains block.
     * @param block
     * @param sep
     * @return
     */
    private boolean doesBlockCoverSeparator(VisionBlock block, Separator sep) {
        return isContained(sep.getRect(), block.getRect());
    }






}