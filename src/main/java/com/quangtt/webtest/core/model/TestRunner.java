package com.quangtt.webtest.core.model;

import java.util.Map;

public abstract class TestRunner {

    PropertyHandler propertyHandler;

    protected void constructPropertyHandler(PropertyLevel level, Map<String, String> properties) {
        this.propertyHandler = new PropertyHandler(level, properties);
    }

    public void delegate(Step step) {
        execute(step);
    }

    public abstract void execute(Step step);

}
