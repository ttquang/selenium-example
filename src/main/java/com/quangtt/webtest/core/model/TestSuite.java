package com.quangtt.webtest.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSuite extends Element {
    List<TestCase> testCases = new ArrayList<>();

    Map<String, Integer> loopTimes = new HashMap<>();

    TestRunner environment;

    public TestSuite(String name) {
        super(name);
        this.constructPropertyHandler(PropertyLevel.TEST_SUITE, new HashMap<>());
    }

    @Override
    public void execute() {
        System.out.println("TestSuite[" + name + "]:START");
        for (TestCase testCase : testCases) {
            System.out.println("TestCase[" + testCase.name + "]:START");
            int loopTime = loopTimes.get(testCase.name);
            for (int i = 0; i < loopTime; i++) {
                testCase.run();
            }
            System.out.println("TestCase[" + testCase.name + "]:PASSED");
        }
        System.out.println("TestSuite[" + name + "]:PASSED");
    }

    public void addTestCase(TestCase testCase) {
        addTestCase(testCase, 1);
    }

    public void addTestCase(TestCase testCase, int loopTime) {
        loopTimes.put(testCase.name, loopTime);
        testCase.propertyHandler.nextHandler = this.propertyHandler;
        testCase.testSuite = this;
        this.testCases.add(testCase);
    }

    public void runWith(TestRunner environment) {
        plugIn(environment);
        execute();
        unPlugin();

    }

    private void plugIn(TestRunner environment) {
        this.environment = environment;
        this.propertyHandler.nextHandler = environment.propertyHandler;
    }

    private void unPlugin() {
        this.environment = null;
        this.propertyHandler.nextHandler = null;
    }

    public void delegate(Step testStep) {
        this.environment.delegate(testStep);
    }

}
