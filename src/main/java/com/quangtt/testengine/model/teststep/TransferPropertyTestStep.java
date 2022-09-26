package com.quangtt.testengine.model.teststep;

public class TransferPropertyTestStep extends SetPropertyTestStep {

    private String selector;

    public TransferPropertyTestStep(String name, String key, String selector) {
        super(name, key);
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }

    @Override
    public void run() {
        testCase.visit(this);
    }

}
