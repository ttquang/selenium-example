package com.quangtt.webtest.template.model;

import com.quangtt.webtest.core.model.Step;

import java.util.List;

public abstract class Element {

    String name;

    public Element(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract Element clone();

    public Step generateStep(String group, List<String> parameters) {
        throw new RuntimeException();
    }

    @Override
    public String toString() {
        return name;
    }

}
