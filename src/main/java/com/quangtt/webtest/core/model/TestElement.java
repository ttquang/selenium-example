package com.quangtt.webtest.core.model;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import static com.quangtt.webtest.core.model.PropertyHandler.PROPERTY_PATTERN;

public abstract class TestElement {

    static String DISABLE_PROPERTY_KEY = "disable";
    String name;

    PropertyHandler propertyHandler;

    String executeCondition;

    public TestElement(String name) {
        this.name = name;
    }

    public abstract void execute();

    public void run() {
        if (evaluateExecuteCondition()) {
            execute();
        }
    }

    public boolean isEnable() {
        return !Boolean.valueOf(propertyHandler.properties.get(DISABLE_PROPERTY_KEY));
    };

    public boolean evaluateExecuteCondition() {
        Map<String, Object> inputs = new HashMap<>();
        Matcher m = PROPERTY_PATTERN.matcher(executeCondition);
        String processedExecuteCondition = executeCondition;
        int i = 0;
        while (m.find()) {
            String placeHolder = m.group();
            String value = this.getProperty(placeHolder);
            processedExecuteCondition = processedExecuteCondition.replace(placeHolder, "value" + i);
            inputs.put("value" + i, value);
            m = PROPERTY_PATTERN.matcher(processedExecuteCondition);
        }

        return ConditionEvaluationUtils.evaluate(processedExecuteCondition, inputs);
    }

    public void putProperty(String key, String value) {
        propertyHandler.put(key, value);
    }

    public String getProperty(String key) {
        return propertyHandler.get(key);
    }

    protected void constructPropertyHandler(PropertyLevel level, Map<String, String> properties) {
        this.propertyHandler = new PropertyHandler(level, properties);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
