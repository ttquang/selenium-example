package com.example.test;

public class DelayStep extends Step {

    public DelayStep(String name) {
        super(name);
    }

    @Override
    public void run(ITestCase testCase) {
        testCase.visit(this);
    }

}
