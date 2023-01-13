package com.quangtt.webtest.core.model;

public class TransferPropertyTestStep extends Step implements PropertyAware, XpathAware {

    String selector;
    String key;

    public TransferPropertyTestStep(String name, String key, String selector) {
        super(name);
        this.selector = selector;
        this.key = key;
    }

    @Override
    public String getSelector() {
        return selector;
    }

    @Override
    public String getKey() {
        return key;
    }
}
