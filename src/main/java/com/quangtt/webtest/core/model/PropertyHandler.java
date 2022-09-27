package com.quangtt.webtest.core.model;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyHandler {

    static Pattern GET_PATTERN = Pattern.compile("\\{(.*)}");
    static Pattern PUT_PATTERN = Pattern.compile("(.*)#(.*)");

    PropertyLevel level;
    Map<String, String> properties;
    PropertyHandler nextHandler;

    public PropertyHandler(PropertyLevel level, Map<String, String> properties) {
        this.level = level;
        this.properties = properties;
    }

    void put(String key, String value) {
        Matcher m = PUT_PATTERN.matcher(key);
        if (m.find()) {
            if (this.level == PropertyLevel.valueOfLabel(m.group(1))) {
                properties.put(m.group(2), value);
            } else {
                this.nextHandler.put(key, value);
            }
        } else {
            properties.put(key, value);
        }
    }

    String get(String key) {
        Matcher m = GET_PATTERN.matcher(key);
        if (m.find()) {
            if (this.level == PropertyLevel.valueOfLabel(m.group(1))) {
                return get(properties.get(m.group(2)));
            } else {
                return this.nextHandler.get(key);
            }
        }
        return key;
    }

}
