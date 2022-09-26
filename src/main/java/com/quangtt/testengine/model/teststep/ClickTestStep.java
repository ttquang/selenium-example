package com.quangtt.testengine.model.teststep;

public class ClickTestStep extends ElementTestStep {

    public ClickTestStep(String name, String delayPeriod, String selector) {
        super(name, delayPeriod, selector);
    }

    @Override
    protected void delegate() {
        testCase.visit(this);
    }

}
