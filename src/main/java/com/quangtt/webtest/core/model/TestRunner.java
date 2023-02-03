package com.quangtt.webtest.core.model;

import java.util.Map;

public abstract class TestRunner {

    PropertyHandler propertyHandler;

    protected void constructPropertyHandler(PropertyLevel level, Map<String, String> properties) {
        this.propertyHandler = new PropertyHandler(level, properties);
    }

    public void delegate(Step testStep) {
        if (testStep instanceof ClickAllElementTestStep) {
            execute((ClickAllElementTestStep) testStep);
        } else if (testStep instanceof ClickStep) {
            execute((ClickStep) testStep);
        } else if (testStep instanceof SelectInputStep) {
            execute((SelectInputStep) testStep);
        } else if (testStep instanceof PropertyTransferDOMValueStep) {
            execute((PropertyTransferDOMValueStep) testStep);
        } else if (testStep instanceof TextInputStep) {
            execute((TextInputStep) testStep);
        } else if (testStep instanceof NavigationToUrlStep) {
            execute((NavigationToUrlStep) testStep);
        } else if (testStep instanceof SwitchToFrameByXpathStep) {
            execute((SwitchToFrameByXpathStep) testStep);
        }
    }

    public abstract void execute(ClickStep testStep);

    public abstract void execute(ClickAllElementTestStep testStep);

    public abstract void execute(TextInputStep testStep);

    public abstract void execute(SelectInputStep testStep);

    public abstract void execute(NavigationToUrlStep testStep);

    public abstract void execute(SwitchToFrameByXpathStep testStep);

    public abstract void execute(PropertyTransferDOMValueStep testStep);

}
