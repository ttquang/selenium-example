package com.quangtt.testengine.model.teststep;

import com.quangtt.testengine.model.testelement.TestStep;

public abstract class PropertyTestStep extends TestStep {

    protected String key;

    public PropertyTestStep(String name, String key) {
        super(name);
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
