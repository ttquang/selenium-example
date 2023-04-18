package com.quangtt.webtest.template.model;


import java.util.ArrayList;
import java.util.List;

public class Template {
    String name;

    List<Element> elements = new ArrayList<>();

    public Template(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Element> getElements() {
        return elements;
    }

}
