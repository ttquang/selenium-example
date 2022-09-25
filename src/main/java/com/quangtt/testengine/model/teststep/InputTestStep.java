package com.quangtt.testengine.model.teststep;

import com.quangtt.testengine.model.testelement.ITestCase;

public class InputTestStep extends ElementTestStep {

    protected String value;

    public InputTestStep(String name, String selector, String value) {
        super(name, selector);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void run(ITestCase testCase) {
        testCase.visit(this);
    }

}
