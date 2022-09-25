package com.example.test;

public class TransferPropertyStep extends SetPropertyStep {

    private String selector;

    public TransferPropertyStep(String name, String key, String selector) {
        super(name, key);
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }

    @Override
    public void run(ITestCase testCase) {
        testCase.visit(this);
    }

}
