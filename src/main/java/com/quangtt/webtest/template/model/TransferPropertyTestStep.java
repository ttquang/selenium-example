package com.quangtt.webtest.template.model;

public class TransferPropertyTestStep extends TestStep implements PropertyAware, ElementAware {

    String selector;
    String key;

    public TransferPropertyTestStep(String name, String key, String selector, long delayPeriod) {
        super(name, delayPeriod);
        this.selector = selector;
        this.key = key;
    }

    @Override
    public String getSelector() {
        return selector;
    }

    @Override
    public void setSelector(String selector) {
        this.selector = selector;
    }

    @Override
    public String getKey() {
        return key;
    }
}
