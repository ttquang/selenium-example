package com.example.test;

public class InputStep extends ElementStep {

    protected String value;

    public InputStep(String name, String selector, String value) {
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
