package com.quangtt.testengine.model.teststep;

import com.quangtt.testengine.model.testelement.TestStep;

public class DelayTestStep extends TestStep {

    public DelayTestStep(String name) {
        super(name);
    }

    @Override
    public void run() {
        testCase.visit(this);
    }

}
