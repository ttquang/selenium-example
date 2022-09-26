package com.quangtt.testengine.model.teststep;

public class SwitchFrameTestStep extends ElementTestStep {

    public SwitchFrameTestStep(String name, String delayPeriod, String selector) {
        super(name, delayPeriod, selector);
    }

    @Override
    protected void delegate() {
        testCase.visit(this);
    }

}
