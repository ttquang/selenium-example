package com.quangtt.testengine.model.teststep;

import com.quangtt.testengine.model.testelement.ITestCase;

public class ClickTestStep extends ElementTestStep {

    public ClickTestStep(String name, String delayPeriod, String selector) {
        super(name, delayPeriod, selector);
    }

    @Override
    protected void delegate(ITestCase testCase) {
        testCase.visit(this);
    }

}
