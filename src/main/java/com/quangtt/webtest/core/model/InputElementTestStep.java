package com.quangtt.webtest.core.model;

public class InputElementTestStep extends TestStep implements ElementAware {
    String selector;
    String value;

    public InputElementTestStep(String name, String selector, String value, long delayPeriod) {
        super(name, delayPeriod);
        this.value = value;
        this.selector = selector;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String getSelector() {
        return selector;
    }
}
