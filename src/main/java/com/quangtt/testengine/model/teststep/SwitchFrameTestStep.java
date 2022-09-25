package com.quangtt.testengine.model.teststep;

import com.quangtt.testengine.model.testelement.ITestCase;

public class SwitchFrameTestStep extends ElementTestStep {

    public SwitchFrameTestStep(String name, String selector) {
        super(name, selector);
    }

    @Override
    public void run(ITestCase testCase) {
        testCase.visit(this);
    }

}
