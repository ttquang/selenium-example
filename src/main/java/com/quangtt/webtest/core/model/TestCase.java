package com.quangtt.webtest.core.model;

import com.quangtt.webtest.core.exception.StepRuntimeException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestCase extends Element {
    TestSuite testSuite;
    List<Step> steps = new ArrayList<>();

    public TestCase(String name) {
        super(name);
        this.constructPropertyHandler(PropertyLevel.TEST_CASE, new HashMap<>());
    }

    public void addStep(Step step) {
        step.propertyHandler.nextHandler = this.propertyHandler;
        step.testCase = this;
        this.steps.add(step);
    }

    public void execute() {
        for (Step step : steps) {
            try {
                step.run();
            } catch (StepRuntimeException ex) {
                ex.printStackTrace();
                throw new StepRuntimeException(step);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new StepRuntimeException(step);
            }
        }
    }

    public void delegate(Step step) {
        testSuite.delegate(step);
    }

}
