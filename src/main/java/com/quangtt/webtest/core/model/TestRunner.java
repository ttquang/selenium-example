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
    public abstract void execute(ClickStep testStep);

    public abstract void execute(ClickAllElementTestStep testStep);

    public abstract void execute(TextInputStep testStep);

    public abstract void execute(SelectStep testStep);

    public abstract void execute(NavigationToUrlStep testStep);

    public abstract void execute(SwitchToFrameByXpathStep testStep);

    public abstract void execute(PropertyTransferDOMValueStep testStep);

}
