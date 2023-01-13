package com.quangtt.webtest.template.model;

public abstract class TestStep extends TestElement {

    long delayPeriod;

    public TestStep(String name, long delayPeriod) {
        super(name);
        this.delayPeriod = delayPeriod;
    }

}
