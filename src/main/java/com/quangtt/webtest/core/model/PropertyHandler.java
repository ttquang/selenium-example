package com.quangtt.webtest.core.model;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyHandler {

    static Pattern PROPERTY_PATTERN = Pattern.compile("\\{(Environment|TestSuite|TestCase|TestStep)#([\\w\\d]+)}");

    PropertyLevel level;
    Map<String, String> properties;
    PropertyHandler nextHandler;

    public PropertyHandler(PropertyLevel level, Map<String, String> properties) {
        this.level = level;
        this.properties = properties;
    }

    void put(String key, String value) {
        Matcher m = PROPERTY_PATTERN.matcher(key);
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
        System.out.println(key);
        Matcher m = PROPERTY_PATTERN.matcher(key);
        String result = key;

        while (m.find()) {
            String placeHolder = m.group();
            String value;
            if (this.level == PropertyLevel.valueOfLabel(m.group(1))) {
                value = properties.get(m.group(2));
            } else {
                value = this.nextHandler.get(placeHolder);
            }
            result = result.replace(placeHolder, value);
            m = PROPERTY_PATTERN.matcher(result);
        }

        return result;
    }

}
