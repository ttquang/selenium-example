package com.quangtt.webtest.template.model;

public class SetPropertyTestStep extends Element implements PropertyAware {
    String key;
    String value;

    public SetPropertyTestStep(String name, String key, String value) {
        super(name);
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

    @Override
    public Element clone() {
        return null;
    }

}
