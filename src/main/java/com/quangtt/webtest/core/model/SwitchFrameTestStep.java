package com.quangtt.webtest.core.model;

public class SwitchFrameTestStep extends TestStep implements ElementAware {
    String selector;

    public String getSelector() {
        return selector;
    }

    public SwitchFrameTestStep(String name, String selector, long delayPeriod) {
        super(name, delayPeriod);
        this.selector = selector;
    }

}
