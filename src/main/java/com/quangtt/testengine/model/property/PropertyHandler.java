package com.quangtt.testengine.model.property;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyHandler implements IPropertyHandler {

    private static Pattern pattern = Pattern.compile("(.*)#(.*)");

    private String level;
    private Map<String, String> properties = new HashMap<>();

    private IPropertyHandler nextHandler;

    public PropertyHandler(String level) {
        this.level = level;
    }

    public PropertyHandler(String level, IPropertyHandler nextHandler) {
        this.level = level;
        this.nextHandler = nextHandler;
    }

    public String get(String key) {
        Matcher m = pattern.matcher(key);
        if (m.find()) {
            String requestLevel = m.group(1);
            if (this.level.equals(requestLevel)) {
                return properties.get(m.group(2));
            } else {
                return this.nextHandler.get(key);
            }
        }
        return Objects.nonNull(properties.get(key)) ? properties.get(key) : "";
    }

    public void put(String key, String value) {
        Matcher m = pattern.matcher(key);
        if (m.find()) {
            String requestLevel = m.group(1);
            if (this.level.equals(requestLevel)) {
                properties.put(m.group(2), value);
            } else {
                this.nextHandler.put(key, value);
            }
        } else {
            properties.put(key, value);
        }
    }


}
