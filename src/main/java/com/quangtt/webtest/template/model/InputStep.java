package com.quangtt.webtest.template.model;

import com.quangtt.webtest.template.service.TemplateUtils;

import java.util.List;

public abstract class InputStep extends Element implements TemplateXpathAware, TemplateInputAware {
    String selector;
    String value;

    public InputStep(String name, String selector, String value) {
        super(name);
        this.value = value;
        this.selector = selector;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getSelector() {
        return selector;
    }

    @Override
    public String toString() {
        return "InputStep - " + selector + " - " + value;
    }

    @Override
    public void processValue(List<String> parameters) {
        this.value = TemplateUtils.processParameter(value, parameters);
    }

    @Override
    public void processSelector(List<String> parameters) {
        this.selector = TemplateUtils.processParameter(selector, parameters);
    }
}
