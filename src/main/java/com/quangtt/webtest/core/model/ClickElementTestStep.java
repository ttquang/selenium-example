package com.quangtt.webtest.core.model;

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
}
