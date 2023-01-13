package com.quangtt.webtest.template.model;


import java.util.ArrayList;
import java.util.List;

public class Template {
    String name;

    boolean isNested = false;
    List<String> parameters = new ArrayList<>();
    List<TestStep> testSteps = new ArrayList<>();

    public Template(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public List<TestStep> getTestSteps() {
        return testSteps;
    }

    public boolean isNested() {
        return isNested;
    }

    public void setNested(boolean nested) {
        isNested = nested;
    }
}
