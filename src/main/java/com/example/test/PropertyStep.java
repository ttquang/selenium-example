package com.example.test;

public abstract class PropertyStep extends Step {

    protected String key;

    public PropertyStep(String name, String key) {
        super(name);
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
