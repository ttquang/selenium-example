package com.example.test;

public class SetPropertyStep extends PropertyStep {

    protected String value;

    public SetPropertyStep(String name, String key, String value) {
        super(name, key);
        this.value = value;
    }

    public SetPropertyStep(String name, String key) {
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
