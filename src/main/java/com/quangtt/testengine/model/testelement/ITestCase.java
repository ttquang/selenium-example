package com.quangtt.testengine.model.testelement;

import com.quangtt.testengine.model.property.IPropertyHandler;
import com.quangtt.testengine.model.teststep.*;

public interface ITestCase {

    void run();

    void setPropertyHandler(IPropertyHandler propertyHandler);

    IPropertyHandler getPropertyHandler();

    void setTestSuite(TestSuite testSuite);

    void visit(ClickTestStep step);
    void visit(DelayTestStep step);
    void visit(InputTestStep step);
    void visit(InputSelectTestStep step);
    void visit(SetPropertyTestStep step);
    void visit(TransferPropertyTestStep step);

    void visit(SwitchFrameTestStep step);

    void visit(LoadPageTestStep step);

}
