package com.quangtt.webtest.core.model;

public abstract class InputStep extends Step implements XpathAware, InputAware {
    String selector;
    String value;

    public InputStep(String name, String selector, String value) {
        super(name);
        this.value = value;
        this.selector = selector;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getSelector() {
        return selector;
    }

}
