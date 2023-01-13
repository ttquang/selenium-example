package com.quangtt.webtest.template.model;

public abstract class TestStep extends TestElement implements Cloneable{

    long delayPeriod;

    public TestStep(String name, long delayPeriod) {
        super(name);
        this.delayPeriod = delayPeriod;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
