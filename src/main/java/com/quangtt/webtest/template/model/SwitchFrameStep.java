package com.quangtt.webtest.template.model;

import com.quangtt.webtest.template.service.TemplateUtils;

import java.util.List;

public class SwitchFrameStep extends Element implements TemplateXpathAware {
    String selector;

    @Override
    public String getSelector() {
        return selector;
    }

    public SwitchFrameStep(String name, String selector) {
        super(name);
        this.selector = selector;
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
