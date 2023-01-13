package com.quangtt.webtest.template.model;

import com.quangtt.webtest.template.service.TemplateUtils;

import java.util.List;

public class TemplateStep extends Element implements TemplateXpathAware, TemplateInputAware {
    String selector;
    String value;

    public TemplateStep(String name, String selector, String value) {
        super(name);
        this.selector = selector;
        this.value = value;
    }

    @Override
    public String getSelector() {
        return selector;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "TemplateStep - " + selector + " - " + value;
    }

    @Override
    public Element clone() {
        return null;
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
