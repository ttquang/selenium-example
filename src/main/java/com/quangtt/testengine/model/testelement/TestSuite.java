package com.quangtt.testengine.model.testelement;


import com.quangtt.testengine.model.property.IPropertyHandler;
import com.quangtt.testengine.model.property.PropertyHandler;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

public class TestSuite extends TestElement {
    private List<ITestCase> testCases = new ArrayList<>();

    private WebDriver webDriver;

    private IPropertyHandler propertyHandler = new PropertyHandler("TestSuite");

    public TestSuite(String name, WebDriver webDriver) {
        super(name);
        this.webDriver = webDriver;
    }

    public void addTestCase(ITestCase testCase) {
        testCase.setPropertyHandler(propertyHandler.generateSubHandler("TestCase", propertyHandler));
        testCase.setWebDriver(webDriver);
        testCases.add(testCase);
    }

    @Override
    public void run() {
        for (ITestCase testCase:testCases) {
            testCase.run();
        }
    }
}
