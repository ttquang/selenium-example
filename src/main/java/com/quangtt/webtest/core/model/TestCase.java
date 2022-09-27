package com.quangtt.webtest.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestCase extends TestElement {
    TestSuite testSuite;
    List<TestStep> testSteps = new ArrayList<>();

    @Override
    public void constructPropertyHandler(Map<String, String> properties) {
        constructPropertyHandler(PropertyLevel.TEST_CASE, properties);
    }

    public void addTestStep(TestStep testStep) {
        testStep.propertyHandler.nextHandler = this.propertyHandler;
        testStep.testCase = this;
        this.testSteps.add(testStep);
    }

    public void run() {
        for (TestStep testStep : testSteps) {
            testStep.run();
        }
    }

    public void delegate(TestStep testStep) {
        testSuite.delegate(testStep);
    }
}
