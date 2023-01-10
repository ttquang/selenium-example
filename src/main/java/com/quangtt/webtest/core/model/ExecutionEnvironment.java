package com.quangtt.webtest.core.model;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ExecutionEnvironment {

    PropertyHandler propertyHandler;

    protected void constructPropertyHandler(PropertyLevel level, Map<String, String> properties) {
        this.propertyHandler = new PropertyHandler(level, properties);
    }

    private void delay(TestStep testStep) {
        if (testStep.delayPeriod > 0) {
            try {
                Thread.sleep(testStep.delayPeriod);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public void delegate(TestStep testStep) {
        delay(testStep);

        if (testStep instanceof ClickAllElementTestStep) {
            execute((ClickAllElementTestStep) testStep);
        } else if (testStep instanceof ClickElementTestStep) {
            execute((ClickElementTestStep) testStep);
        } else if (testStep instanceof InputSelectElementTestStep) {
            execute((InputSelectElementTestStep) testStep);
        } else if (testStep instanceof SetPropertyTestStep) {
            execute((SetPropertyTestStep) testStep);
        } else if (testStep instanceof TransferPropertyTestStep) {
            execute((TransferPropertyTestStep) testStep);
        } else if (testStep instanceof InputElementTestStep) {
            execute((InputElementTestStep) testStep);
        } else if (testStep instanceof LoadPageTestStep) {
            execute((LoadPageTestStep) testStep);
        } else if (testStep instanceof SwitchFrameTestStep) {
            execute((SwitchFrameTestStep) testStep);
        }
    }

    public void execute(ClickElementTestStep testStep) {
        String selector = testStep.getProperty(testStep.getSelector());
        click(selector);
    }

    public void execute(ClickAllElementTestStep testStep) {
        String selector = testStep.getProperty(testStep.getSelector());
        clickAll(selector);
    }

    public void execute(InputElementTestStep testStep) {
        String selector = testStep.getProperty(testStep.getSelector());
        String value = testStep.getProperty(testStep.getValue());
        input(selector, value);
    }

    public void execute(InputSelectElementTestStep testStep) {
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

    public void execute(LoadPageTestStep testStep) {
        String url = testStep.getProperty(testStep.getUrl());
        get(url);
    }

    public void execute(SwitchFrameTestStep testStep) {
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
        testStep.putProperty(key, value);
    }

    protected abstract void get(String url);

    protected abstract void switchTo(String selector);

    protected abstract void select(String selector, String type, String value);

    protected abstract void click(String selector);

    protected abstract void clickAll(String selector);

    protected abstract void input(String selector, String value);

    protected abstract String getAttribute(String selector, String attribute);
}
