package com.quangtt.testengine.model.teststep;

import com.quangtt.testengine.model.testelement.ITestCase;

public class SwitchFrameTestStep extends ElementTestStep {

    public SwitchFrameTestStep(String name, String delayPeriod, String selector) {
        super(name, delayPeriod, selector);
    }

    @Override
    protected void delegate(ITestCase testCase) {
        testCase.visit(this);
    }

}
