package com.quangtt.webtest.template.model;

public class SwitchFrameTestStep extends TestStep implements ElementAware {
    String selector;

    @Override
    public String getSelector() {
        return selector;
    }

    @Override
    public void setSelector(String selector) {
        this.selector = selector;
    }

    public SwitchFrameTestStep(String name, String selector, long delayPeriod) {
        super(name, delayPeriod);
        this.selector = selector;
    }

}
