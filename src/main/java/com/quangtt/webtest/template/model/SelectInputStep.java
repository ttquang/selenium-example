package com.quangtt.webtest.template.model;

import com.quangtt.webtest.core.StepFactory;
import com.quangtt.webtest.core.model.Step;

import java.util.List;

public class SelectInputStep extends InputStep {
    public SelectInputStep(String name, String selector, String value) {
        super(name, selector, value);
    }

    @Override
    public Step generateStep(String group, List<String> parameters) {
        SelectInputStep tmp = (SelectInputStep) this.clone();
        tmp.processSelector(parameters);
        tmp.processValue(parameters);
        return StepFactory.createSelectInputStep(group + "." + this.name, tmp, tmp);
    }

    @Override
    public Element clone() {
        return new SelectInputStep(name, selector, value);
    }
}
