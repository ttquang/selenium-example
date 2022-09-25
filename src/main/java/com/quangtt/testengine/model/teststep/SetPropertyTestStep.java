package com.quangtt.testengine.model.teststep;

import com.quangtt.testengine.model.testelement.ITestCase;

public class SetPropertyTestStep extends PropertyTestStep {

    protected String value;

    public SetPropertyTestStep(String name, String key, String value) {
        super(name, key);
        this.value = value;
    }

    public SetPropertyTestStep(String name, String key) {
        super(name, key);
    }

    public String getValue() {
        return value;
    }

    @Override
    public void run(ITestCase testCase) {
        testCase.visit(this);
    }

}
