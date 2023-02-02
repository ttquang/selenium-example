package com.quangtt.webtest.core.model;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        } else if (testStep instanceof SetPropertyTestStep) {
            execute((SetPropertyTestStep) testStep);
        } else if (testStep instanceof TransferPropertyTestStep) {
            execute((TransferPropertyTestStep) testStep);
        } else if (testStep instanceof TextInputStep) {
            execute((TextInputStep) testStep);
        } else if (testStep instanceof LoadPageStep) {
            execute((LoadPageStep) testStep);
        } else if (testStep instanceof SwitchFrameStep) {
            execute((SwitchFrameStep) testStep);
        }
    }

    public void execute(ClickStep testStep) {
        String selector = testStep.getProperty(testStep.getSelector());
        click(selector);
    }

    public void execute(ClickAllElementTestStep testStep) {
        String selector = testStep.getProperty(testStep.getSelector());
        clickAll(selector);
    }

    public void execute(TextInputStep testStep) {
        String selector = testStep.getProperty(testStep.getSelector());
        String value = testStep.getProperty(testStep.getValue());
        input(selector, value);
    }

    public void execute(SelectInputStep testStep) {
        String selector = testStep.getProperty(testStep.getSelector());
        String type = "Label";
        String value = "";
        Pattern pattern = Pattern.compile("(Index|Label|Value)#(.*)");
        Matcher m = pattern.matcher(testStep.getValue());
        if (m.find()) {
            type = m.group(1);
            value = testStep.getProperty(m.group(2));
        }
        select(selector, type, value);
    }

    public void execute(LoadPageStep testStep) {
        String url = testStep.getProperty(testStep.getUrl());
        get(url);
    }

    public void execute(SwitchFrameStep testStep) {
        String selector = testStep.getProperty(testStep.getSelector());
        switchTo(selector);
    }

    public void execute(SetPropertyTestStep testStep) {
        String key = testStep.getProperty(testStep.getKey());
        String value = testStep.getProperty(testStep.getValue());
        testStep.putProperty(key, value);
    }

    public void execute(TransferPropertyTestStep testStep) {
        String selector = testStep.getProperty(testStep.getSelector());
        String key = testStep.getProperty(testStep.getKey());
        String value = getAttribute(selector,"value");
        testStep.putProperty("{" + key + "}", value);
    }

    protected abstract void get(String url);

    protected abstract void switchTo(String selector);

    protected abstract void select(String selector, String type, String value);

    protected abstract void click(String selector);

    protected abstract void clickAll(String selector);

    protected abstract void input(String selector, String value);

    protected abstract String getAttribute(String selector, String attribute);
}
