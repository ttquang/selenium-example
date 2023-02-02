package com.quangtt.webtest.core.model;

import java.util.Map;

public abstract class Element {

    String name;

    PropertyHandler propertyHandler;

    public Element(String name) {
        this.name = name;
    }

    public abstract void execute();

    public void run() {
        System.out.println("TestStep[" + name + "]:START");
        execute();
        System.out.println("TestStep[" + name + "]:PASSED");
    }


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
