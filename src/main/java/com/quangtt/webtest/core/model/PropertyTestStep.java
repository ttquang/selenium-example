package com.quangtt.webtest.core.model;

public abstract class PropertyTestStep extends TestStep {
    String key;

    public PropertyTestStep(String name, String key, long delayPeriod) {
        super(name, delayPeriod);
        this.key = key;
    }

}
