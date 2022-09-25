package com.quangtt.testengine.model.testelement;

public abstract class TestStep {

    private String name;

    public TestStep(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void run(ITestCase testCase);

}
