package com.quangtt.webtest.core.model;

import java.util.Map;

public abstract class TestStep extends TestElement {
    TestCase testCase;

    public void run() {
        testCase.delegate(this);
    }

    @Override
    public void constructPropertyHandler(Map<String, String> properties) {
        constructPropertyHandler(PropertyLevel.TEST_STEP, properties);
    }
}
