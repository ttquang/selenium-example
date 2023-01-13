package com.quangtt.webtest.core.model;

public abstract class FrameStep extends Step implements XpathAware {
    String selector;

    public FrameStep(String name, String selector) {
        super(name);
        this.selector = selector;
    }

    @Override
    public String getSelector() {
        return selector;
    }

}
