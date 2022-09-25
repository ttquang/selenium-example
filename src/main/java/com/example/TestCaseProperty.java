package com.example;

import java.util.HashMap;
import java.util.Map;

public class TestCaseProperty {
    private static TestCaseProperty instance = null;

    public Map<String,String> properties = new HashMap<>();

    private TestCaseProperty() {}

    public static TestCaseProperty getInstance()
    {
        if (instance == null)
            instance = new TestCaseProperty();

        return instance;
    }

    public void put(String key, String value) {
        properties.put(key, value);
    }

    public String get(String key) {
        return properties.get(key);
    }

    public void print() {
        System.out.println(properties);
    }
}
