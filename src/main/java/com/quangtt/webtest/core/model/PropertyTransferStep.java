package com.quangtt.webtest.core.model;

public abstract class PropertyTransferStep extends Step {

    String target;

    public PropertyTransferStep(String name, String target) {
        super(name);
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

}
