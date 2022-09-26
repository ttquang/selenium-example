package com.quangtt.testengine.model.testelement;

public abstract class TestStep {

    private String name;

    protected TestCase testCase;

    public TestStep(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTestCase(TestCase testCase) {
        this.testCase = testCase;
    }

    public abstract void run();

}
