package com.quangtt.testengine.model.property;

public interface IPropertyHandler {
    String get(String key);

    void put(String key, String value);

    IPropertyHandler generateSubHandler(String level, IPropertyHandler propertyHandler);

}
