package com.quangtt.webtest.template.model;

import com.quangtt.webtest.core.StepFactory;
import com.quangtt.webtest.core.model.Step;
import com.quangtt.webtest.template.service.TemplateUtils;

import java.util.List;

public class ClickStep extends Element implements TemplateXpathAware {
    String selector;

    public ClickStep(String name, String selector) {
        super(name);
        this.selector = selector;
    }

    @Override
    public String getSelector() {
        return selector;
    }

    @Override
    public String toString() {
        return "ClickStep - " + selector;
    }

    @Override
    public Element clone() {
        return new ClickStep(name, selector);
    }

    @Override
    public Step generateStep(String group, List<String> parameters) {
        ClickStep tmp = (ClickStep) this.clone();
        tmp.processSelector(parameters);
        return StepFactory.createClickStep(group + "." + this.name, tmp);
    }

    @Override
    public void processSelector(List<String> parameters) {
        this.selector = TemplateUtils.processParameter(selector, parameters);
    }
}
