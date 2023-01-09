package com.quangtt.webtest.core.model;

public class SetPropertyTestStep extends TestStep implements PropertyAware{
    String key;
    String value;

    public SetPropertyTestStep(String name, String key, String value, long delayPeriod) {
        super(name, delayPeriod);
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String getKey() {
        return key;
    }
}
