package com.quangtt.webtest.core.model;

import java.util.HashMap;

public abstract class Step extends Element {
    TestCase testCase;

    public Step(String name) {
        super(name);
        this.constructPropertyHandler(PropertyLevel.TEST_STEP, new HashMap<>());
    }

    public void execute() {
        testCase.delegate(this);
    }

}
