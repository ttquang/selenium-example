package com.quangtt.webtest.core.model;

import java.util.HashMap;

public abstract class TestStep extends TestElement implements Cloneable {
    TestCase testCase;

    long delayPeriod;

    public TestStep(String name, long delayPeriod) {
        super(name);
        this.delayPeriod = delayPeriod;
        this.constructPropertyHandler(PropertyLevel.TEST_STEP, new HashMap<>());
    }

    public void execute() {
        testCase.delegate(this);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


}
