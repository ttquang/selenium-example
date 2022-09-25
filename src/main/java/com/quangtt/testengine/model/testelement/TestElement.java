package com.quangtt.testengine.model.testelement;

public abstract class TestElement {
    private String name;

    public TestElement(String name) {
        this.name = name;
    }

    public abstract void run();
}
