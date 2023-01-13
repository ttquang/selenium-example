package com.quangtt.webtest.template.model;

public abstract class TestElement {

    String name;

    public TestElement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
