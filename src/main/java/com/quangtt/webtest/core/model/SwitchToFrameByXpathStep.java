package com.quangtt.webtest.core.model;

public class SwitchToFrameByXpathStep extends SwitchToFrame implements XpathAware {
    String selector;

    public SwitchToFrameByXpathStep(String name, String selector) {
        super(name);
        this.selector = selector;
    }

    @Override
    public String getSelector() {
        return null;
    }
}
