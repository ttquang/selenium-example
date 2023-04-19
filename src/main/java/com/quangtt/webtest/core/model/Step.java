package com.quangtt.webtest.core.model;

import java.util.HashMap;
import java.util.Map;

public class Step extends Element {
    TestCase testCase;

    String type;
    Map<String, String> parameters = new HashMap<>();

    public Step(String name) {
        super(name);
        this.constructPropertyHandler(PropertyLevel.TEST_STEP, new HashMap<>());
    }

    public Step(String name, String type, Map<String, String> parameters) {
        super(name);
        this.parameters = parameters;
        this.type = type;
        this.constructPropertyHandler(PropertyLevel.TEST_STEP, new HashMap<>());
    }

    public void execute() {
        testCase.delegate(this);
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getType() {
        return type;
    }
}
