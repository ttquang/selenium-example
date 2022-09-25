package com.quangtt.testengine.model.teststep;

import com.quangtt.testengine.model.testelement.ITestCase;

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
    protected void delegate(ITestCase testCase) {
        testCase.visit(this);
    }

}
