package com.quangtt.testengine.model.teststep;

public class InputSelectTestStep extends InputTestStep {

    public InputSelectTestStep(String name, String delayPeriod, String selector, String value) {
        super(name, delayPeriod, selector, value);
    }

    @Override
    protected void delegate() {
        testCase.visit(this);
    }

}
