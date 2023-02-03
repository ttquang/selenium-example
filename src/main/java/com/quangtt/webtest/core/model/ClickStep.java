package com.quangtt.webtest.core.model;

public class ClickStep extends Step implements XpathAware {
    String selector;

    public ClickStep(String name, String selector) {
        super(name);
        this.selector = selector;
    }

    @Override
    public String getSelector() {
        return selector;
    }

}