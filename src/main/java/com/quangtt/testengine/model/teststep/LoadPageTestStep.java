package com.quangtt.testengine.model.teststep;

import com.quangtt.testengine.model.testelement.ITestCase;
import com.quangtt.testengine.model.testelement.TestStep;

public class LoadPageTestStep extends TestStep {

    private String url;

    public LoadPageTestStep(String name, String url) {
        super(name);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public void run(ITestCase testCase) {
        testCase.visit(this);
    }

}
