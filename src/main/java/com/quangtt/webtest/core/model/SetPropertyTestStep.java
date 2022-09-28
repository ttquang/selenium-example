package com.quangtt.webtest.core.model;

public class SetPropertyTestStep extends PropertyTestStep {
    String value;

    public SetPropertyTestStep(String name, String key, String value, long delayPeriod) {
        super(name, key, delayPeriod);
        this.value = value;
    }

}
