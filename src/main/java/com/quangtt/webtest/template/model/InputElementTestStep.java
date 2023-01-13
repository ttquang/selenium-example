package com.quangtt.webtest.template.model;

public class InputElementTestStep extends TestStep implements ElementAware, InputAware {
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

    public void setValue(String value) {
        this.value = value;
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
        return "InputElementTestStep - " + selector + " - " + value;
    }
}
