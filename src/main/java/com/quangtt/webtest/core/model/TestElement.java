package com.quangtt.webtest.core.model;

import java.util.Map;

public abstract class TestElement {

    static String DISABLE_PROPERTY_KEY = "disable";
    String name;

    PropertyHandler propertyHandler;

    public TestElement(String name) {
        this.name = name;
    }

    public abstract void execute();

    public void run() {
        if (isEnable()) {
            execute();
        }
    }

    public boolean isEnable() {
        return !Boolean.valueOf(propertyHandler.properties.get(DISABLE_PROPERTY_KEY));
    };

    public void putProperty(String key, String value) {
        propertyHandler.put(key, value);
    }

    public String getProperty(String key) {
        return propertyHandler.get(key);
    }

    protected void constructPropertyHandler(PropertyLevel level, Map<String, String> properties) {
        this.propertyHandler = new PropertyHandler(level, properties);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
