package com.quangtt.webtest.template.model;

import com.quangtt.webtest.template.service.TemplateUtils;

import java.util.List;

public class TransferPropertyTestStep extends Element implements PropertyAware, TemplateXpathAware {

    String selector;
    String key;

    public TransferPropertyTestStep(String name, String key, String selector) {
        super(name);
        this.selector = selector;
        this.key = key;
    }

    @Override
    public String getSelector() {
        return selector;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Element clone() {
        return null;
    }

    @Override
    public void processSelector(List<String> parameters) {
        this.selector = TemplateUtils.processParameter(selector, parameters);
    }
}