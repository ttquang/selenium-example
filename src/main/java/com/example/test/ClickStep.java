package com.example.test;

public class ClickStep extends ElementStep {

    public ClickStep(String name, String selector) {
        super(name, selector);
    }

    @Override
    public void run(ITestCase testCase) {
        testCase.visit(this);
    }

}
