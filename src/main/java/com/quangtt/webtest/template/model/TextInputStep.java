package com.quangtt.webtest.template.model;

import com.quangtt.webtest.core.StepFactory;
import com.quangtt.webtest.core.model.Step;

import java.util.List;

public class TextInputStep extends InputStep {
    public TextInputStep(String name, String selector, String value) {
        super(name, selector, value);
    }

    @Override
    public Step generateStep(String group, List<String> parameters) {
        TextInputStep tmp = (TextInputStep) this.clone();
        tmp.processSelector(parameters);
        tmp.processValue(parameters);
        return StepFactory.createTextInputStep(group + "." + this.name, tmp, tmp);
    }

    @Override
    public Element clone() {
        return new TextInputStep(name, selector, value);
    }
}
