package com.quangtt.webtest.template.model;


public class TemplateTestStep extends TestStep implements ElementAware, InputAware {
    String selector;
    String value;

    public TemplateTestStep(String name, String selector, String value, long delayPeriod) {
        super(name, delayPeriod);
        this.selector = selector;
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
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TemplateTestStep - " + selector + " - " + value;
    }
}
