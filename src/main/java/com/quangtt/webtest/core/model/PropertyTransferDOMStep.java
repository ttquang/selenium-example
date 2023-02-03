package com.quangtt.webtest.core.model;

public class PropertyTransferDOMStep extends PropertyTransferStep implements XpathAware {

    String selector;
    String attribute;

    public PropertyTransferDOMStep(String name, String target, String selector, String attribute) {
        super(name, target);
        this.selector = selector;
        this.attribute = attribute;
    }

    @Override
    public String getSelector() {
        return selector;
    }

    public String getAttribute() {
        return attribute;
    }

}
