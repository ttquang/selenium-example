package com.example.test;

public abstract class ElementStep extends Step {

    protected String selector;

    public ElementStep(String name, String selector) {
        super(name);
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }

}
