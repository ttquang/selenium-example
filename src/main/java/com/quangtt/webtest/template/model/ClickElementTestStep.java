package com.quangtt.webtest.template.model;

public class ClickElementTestStep extends TestStep implements ElementAware {
    String selector;

    public ClickElementTestStep(String name, String selector, long delayPeriod) {
        super(name, delayPeriod);
        this.selector = selector;
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
    public String toString() {
        return "ClickElementTestStep - " + selector;
    }
}
