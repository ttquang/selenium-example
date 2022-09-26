package com.quangtt.testengine.model.teststep;

public class InputTestStep extends ElementTestStep {

    protected String value;

    public InputTestStep(String name, String delayPeriod, String selector, String value) {
        super(name, delayPeriod, selector);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    protected void delegate() {
        testCase.visit(this);
    }

}
