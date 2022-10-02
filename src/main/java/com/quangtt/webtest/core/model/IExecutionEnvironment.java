package com.quangtt.webtest.core.model;

public abstract class IExecutionEnvironment {

    private void delay(TestStep testStep) {
        if (testStep.delayPeriod > 0) {
            try {
                Thread.sleep(testStep.delayPeriod);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public void delegate(TestStep testStep) {
        delay(testStep);

        if (testStep instanceof ClickAllElementTestStep) {
            execute((ClickAllElementTestStep) testStep);
        } else if (testStep instanceof ClickElementTestStep) {
            execute((ClickElementTestStep) testStep);
        } else if (testStep instanceof InputSelectElementTestStep) {
            execute((InputSelectElementTestStep) testStep);
        } else if (testStep instanceof SetPropertyTestStep) {
            execute((SetPropertyTestStep) testStep);
        } else if (testStep instanceof TransferPropertyTestStep) {
            execute((TransferPropertyTestStep) testStep);
        } else if (testStep instanceof InputElementTestStep) {
            execute((InputElementTestStep) testStep);
        } else if (testStep instanceof LoadPageTestStep) {
            execute((LoadPageTestStep) testStep);
        } else if (testStep instanceof SwitchFrameTestStep) {
            execute((SwitchFrameTestStep) testStep);
        }
    }

    public abstract void execute(ClickElementTestStep testStep);

    public abstract void execute(ClickAllElementTestStep testStep);

    public abstract void execute(InputElementTestStep testStep);

    public abstract void execute(InputSelectElementTestStep testStep);

    public abstract void execute(LoadPageTestStep testStep);

    public abstract void execute(SwitchFrameTestStep testStep);

    public abstract void execute(SetPropertyTestStep testStep);

    public abstract void execute(TransferPropertyTestStep testStep);

}
