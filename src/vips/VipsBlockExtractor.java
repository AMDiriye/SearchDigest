package vips;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.Element;

public class VipsBlockExtractor implements BlockExtractor {

    Logger l = Logger.getRootLogger();
    private NodeFeature nodeFeature = NodeFeature.getInstance();
    private Map<String, String> rules;
    private Map<String, String> ruleTypes;
    private Set<String> allDividableNode = new HashSet<String>();
    DivideRuleFactory ruleFactory = new DivideRuleFactory();

    public VipsBlockExtractor() {
        ruleFactory.setAllDividableNode(allDividableNode);
    }

    @Override
    public DivideRule dividable(Node ele, int level) {
        DivideRule matchedRule = null;

        if (null != ele) {
           String ruleCode = "";
           if (NodeFeature.getInstance().isTextNode(ele)) {
                ruleCode = rules.get("TextNode");
            } else {
                String tagName = ((Element)ele).tagName();
                tagName = null != tagName ? tagName.toUpperCase() : "";
                Set<String> set = rules.keySet();
                if (set.contains(tagName)) {
                    ruleCode = rules.get(tagName.toUpperCase());
                } else {
                    ruleCode = rules.get("OTHER");
                }
            }
           
            matchedRule = execute(ruleCode, ele, level);

            //TODO make sure this works
//        l.debug("Rule Code: " + ruleCode);
            if (matchedRule.dividable() == BlockExtractor.Dividable) {
                allDividableNode.add(ele.baseUri());
            }
        }
        return matchedRule;
    }

    private DivideRule execute(String ruleCode, Node ele, int level) {
        return execute(ruleCode.split(","), ele, level);
    }

    private DivideRule execute(String[] ruleCode, Node ele, int level) {
        for (String rule : ruleCode) {
            DivideRule dr = ruleFactory.create(ruleTypes.get(rule));
            if (dr.match(ele, level)) {
                return dr;
            }
        }
        return null;
    }

    public String getReferrer() {
        return ruleFactory.getReferrer();
    }

    public void setReferrer(String referrer) {
        ruleFactory.setReferrer(referrer);
    }

    public double getPageSize() {
        return ruleFactory.getPageSize();
    }

    public void setPageSize(double pageSize) {
        ruleFactory.setPageSize(pageSize);
    }

    public double getThreshold() {
        return ruleFactory.getThreshold();
    }

    public void setThreshold(double threshold) {
        ruleFactory.setThreshold(threshold);
    }

}