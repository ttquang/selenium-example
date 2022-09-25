package com.example.test;

public abstract class Step {

    private String name;

    public Step(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void run(ITestCase testCase);

}
