package com.example.test;

import com.example.TestCaseProperty;

public class PropertyVisitor implements IPropertyVisitor {

//    private Map<String, String> properties = new HashMap<>();

    public PropertyVisitor() {
    }

    @Override
    public void put(SetPropertyStep step) {
        TestCaseProperty.getInstance().put(step.getKey(),step.getValue());
//        properties.put(step.getKey(),step.getValue());
    }

}
