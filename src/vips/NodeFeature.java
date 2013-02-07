package vips;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

public class NodeFeature {
	
	//TODO remove line-breaks
	
    Logger l = Logger.getRootLogger();
    static final NodeFeature instance = new NodeFeature();

    public static NodeFeature getInstance() {
        return instance;
    }

    /**
     * A node that can be seen through the browser. The node's width and height are not equal to zero.
     * @param ele
     * @return
     */
    public boolean isValidNode(Node ele) {
        if (ele instanceof TextNode) {
            return null != ((TextNode) ele).text() && ((TextNode) ele).text().trim().length() > 0;
        } else {
            return ele.childNodes().size() > 0;
        }
    }

    /**
     * A node has at least one valid child.
     * @param ele
     * @return
     */
    public boolean hasValidChildren(Node ele) {
        List<Node> children = ele.childNodes();
        if (null != children && children.size() > 0) {
            for (int i = 0; i < children.size(); i++) {
                if (isValidNode(children.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Does all of child node are virtual text node?
     * @param ele
     * @return
     */
    public boolean areChildrenVirtualTextNode(Node ele) {
        List<Node> children = ele.childNodes();
        if (null != children && children.size() > 0) {
            for (int i = 0; i < children.size(); i++) {
                if (!isVirtualTextNode(children.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    /**
     * Return true if one of the child nodes of the DOM node is text node or virtual text node.
     * @param ele
     * @return
     */
    public boolean hasTextOrVirtualTextNode(Node ele) {
        List<Node> children = ele.childNodes();
        if (null != children && children.size()> 0) {
            for (int i = 0; i < children.size(); i++) {
                if (isTextNode(children.get(i)) || isVirtualTextNode(children.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Return true if one of the child nodes if the DOM node has HTML tag HR.
     * @param ele
     * @return
     */
    public boolean isChildNodeHRTag(Node ele) {
        List<Node> children = ele.childNodes();
        if (null != children && children.size() > 0) {
            for (int i = 0; i < children.size(); i++) {
                Node child = children.get(i);
                if (((Element) child).tagName().toLowerCase().contains("hr")) {
                    return true;
                }
            }
        }
        return false;
    }

    //TODO check classnames and style
    /**
     * Return true if the background color of this node is different from one of its children's.
     * @param ele
     * @return
     */
    public boolean hasDifferentClassAndStyleWithChild(Node ele) {
        Set<String> classnames = ((Element)ele).classNames();
        String style = ele.attr("style");
        List<Node> children = ele.childNodes();
        if (null != children && children.size() > 0) {
            for (int i = 0; i < children.size(); i++) {
                Node child = children.get(i);
                if (!isTextNode(child) && isValidNode(child)) {
 
                    if (!equal(style, child.attr("style")) || !classnames.equals(((Element)child).classNames())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean equal(String str1, String str2) {
        if (null != str1) {
            // str1 != null, compare(str1, str2)
            return str1.equals(str2);
        } else if (null != str2) {
            // str1 = null, str2 != null;
            return false;
        } else {
            // str1 = null, str2 = null.
            return true;
        }
    }



    /**
     * How many children the element has?
     * @param ele
     * @return
     */
    public int howManyChildren(Node ele) {
        List<Node> children = ele.childNodes();
        if (null != children && children.size() > 0) {
            return children.size();
        } else {
            return 0;
        }
    }

    /**
     * The DOM node corresponding to free text, which does not have and html tag.
     * @param ele
     * @return
     */
    public boolean isTextNode(Node ele) {
        return ele instanceof TextNode;
    }

    /**
     * 1. Inline node with only text node children is a virtual text node.<br/>
     * 2. Inline node with only text node and virtual text node children is a virtual text node.
     * @param ele
     * @return
     */
    public boolean isVirtualTextNode(Node ele) {
        if (!isTextNode(ele)) {
            return false;
        } else {
//            String tagName = ele.getTagName();
//            Node node = ele.convertToW3CNode();
//            int nodeType = node.getNodeType();
//            l.debug(tagName + " - " + nodeType);
            List<Node>children = ele.childNodes();
            if (null != children && children.size() > 0) {
                for (int i = 0; i < children.size(); i++) {
                    if (!isVirtualTextNode(children.get(i))) {
                        return false;
                    }
                }
                return true;
            } else {
                return isTextNode(ele);
            }
        }
    }
}