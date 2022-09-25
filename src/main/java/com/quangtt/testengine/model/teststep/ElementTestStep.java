package com.quangtt.testengine.model.teststep;

import com.quangtt.testengine.model.testelement.ITestCase;
import com.quangtt.testengine.model.testelement.TestStep;

public abstract class ElementTestStep extends TestStep {

    protected String selector;

    protected String delayPeriod;

    public ElementTestStep(String name, String delayPeriod, String selector) {
        super(name);
        this.selector = selector;
        this.delayPeriod = delayPeriod;
    }

    public String getSelector() {
        return selector;
    }

    @Override
    public void run(ITestCase testCase) {
        Long delayPeriod1 = Long.valueOf(delayPeriod);
        if (delayPeriod1 > 0) {
            try {
                Thread.sleep(Long.valueOf(delayPeriod));
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        delegate(testCase);
    }

    protected abstract void delegate(ITestCase testCase);
}
