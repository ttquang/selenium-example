package com.example.test;

public class InputSelectStep extends InputStep {

    public InputSelectStep(String name, String selector, String value) {
        super(name, selector, value);
    }

    @Override
    public void run(ITestCase testCase) {
        testCase.visit(this);
    }

}
