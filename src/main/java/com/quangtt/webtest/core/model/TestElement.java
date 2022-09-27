package com.quangtt.webtest.core.model;

import java.util.Map;

public abstract class TestElement {
    String name;

    PropertyHandler propertyHandler;

    public abstract void run();

    public void putProperty(String key, String value) {
        propertyHandler.put(key, value);
    }

    public String getProperty(String key) {
        return propertyHandler.get(key);
    }

    protected void constructPropertyHandler(PropertyLevel level, Map<String, String> properties) {
        this.propertyHandler = new PropertyHandler(level, properties);
    }

    public abstract void constructPropertyHandler(Map<String, String> properties);

}