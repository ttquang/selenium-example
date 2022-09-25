package com.example.test;

public class SwitchFrameStep extends ElementStep {

    public SwitchFrameStep(String name, String selector) {
        super(name, selector);
    }

    @Override
    public void run(ITestCase testCase) {
        testCase.visit(this);
    }

}
