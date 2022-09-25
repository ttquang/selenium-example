package com.quangtt.testengine.model.teststep;

import com.quangtt.testengine.model.testelement.ITestCase;

public class InputSelectTestStep extends InputTestStep {

    public InputSelectTestStep(String name, String delayPeriod, String selector, String value) {
        super(name, delayPeriod, selector, value);
    }

    @Override
    protected void delegate(ITestCase testCase) {
        testCase.visit(this);
    }

}
