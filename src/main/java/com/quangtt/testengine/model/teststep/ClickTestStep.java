package com.quangtt.testengine.model.teststep;

import com.quangtt.testengine.model.testelement.ITestCase;

public class ClickTestStep extends ElementTestStep {

    public ClickTestStep(String name, String selector) {
        super(name, selector);
    }

    @Override
    public void run(ITestCase testCase) {
        testCase.visit(this);
    }

}
