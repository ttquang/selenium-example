package com.quangtt.webtest.core.api;

import com.quangtt.webtest.core.model.ExecutionEnvironment;
import com.quangtt.webtest.core.model.TestSuite;

public class ApplicationTest {
    public static void main(String[] args) {
        TestSuite testSuite = new TestSuite();
        ExecutionEnvironment environment = new ExecutionEnvironment();

        testSuite.runWith(environment);
    }
}
