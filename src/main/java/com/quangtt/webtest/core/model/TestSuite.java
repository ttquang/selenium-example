package com.quangtt.webtest.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestSuite extends TestElement {
    List<TestCase> testCases = new ArrayList<>();

    ExecutionEnvironment environment;

    @Override
    public void constructPropertyHandler(Map<String, String> properties) {
        constructPropertyHandler(PropertyLevel.TEST_SUITE, properties);
    }

    public void addTestCase(TestCase testCase) {
        testCase.propertyHandler.nextHandler = this.propertyHandler;
        testCase.testSuite = this;
        this.testCases.add(testCase);
    }

    public void runWith(ExecutionEnvironment environment) {
        this.environment = environment;
        run();
    }

    public void run() {
        for (TestCase testCase : testCases) {
            testCase.run();
        }
    }

    public void delegate(TestStep testStep) {
        this.environment.delegate(testStep);
    }
}
