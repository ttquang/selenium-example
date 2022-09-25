package com.quangtt.testengine.model.testelement;

import com.quangtt.testengine.model.teststep.*;

public interface ITestCase {

    void run();

    void visit(ClickTestStep step);
    void visit(DelayTestStep step);
    void visit(InputTestStep step);
    void visit(InputSelectTestStep step);
    void visit(SetPropertyTestStep step);
    void visit(TransferPropertyTestStep step);

    void visit(SwitchFrameTestStep step);

}
