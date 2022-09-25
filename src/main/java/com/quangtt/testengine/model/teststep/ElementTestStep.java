package com.quangtt.testengine.model.teststep;

import com.quangtt.testengine.model.testelement.TestStep;

public abstract class ElementTestStep extends TestStep {

    protected String selector;

    public ElementTestStep(String name, String selector) {
        super(name);
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }

}
