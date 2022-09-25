package com.quangtt.testengine.model.teststep;

import com.quangtt.testengine.model.testelement.ITestCase;
import com.quangtt.testengine.model.testelement.TestStep;

public class DelayTestStep extends TestStep {

    public DelayTestStep(String name) {
        super(name);
    }

    @Override
    public void run(ITestCase testCase) {
        testCase.visit(this);
    }

}
