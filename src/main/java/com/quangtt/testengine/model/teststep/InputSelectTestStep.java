package com.quangtt.testengine.model.teststep;

import com.quangtt.testengine.model.testelement.ITestCase;

public class InputSelectTestStep extends InputTestStep {

    public InputSelectTestStep(String name, String selector, String value) {
        super(name, selector, value);
    }

    @Override
    public void run(ITestCase testCase) {
        testCase.visit(this);
    }

}
