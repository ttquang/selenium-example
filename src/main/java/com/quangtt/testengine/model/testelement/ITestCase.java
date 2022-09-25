package com.quangtt.testengine.model.testelement;

import com.quangtt.testengine.model.property.IPropertyHandler;
import com.quangtt.testengine.model.teststep.*;
import org.openqa.selenium.WebDriver;

public interface ITestCase {

    void run();

    void setPropertyHandler(IPropertyHandler propertyHandler);

    void setWebDriver(WebDriver webDriver);

    void visit(ClickTestStep step);
    void visit(DelayTestStep step);
    void visit(InputTestStep step);
    void visit(InputSelectTestStep step);
    void visit(SetPropertyTestStep step);
    void visit(TransferPropertyTestStep step);

    void visit(SwitchFrameTestStep step);

    void visit(LoadPageTestStep step);

}
