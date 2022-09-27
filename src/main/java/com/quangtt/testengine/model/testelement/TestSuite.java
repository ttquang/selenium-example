package com.quangtt.testengine.model.testelement;


import com.quangtt.testengine.model.property.IPropertyHandler;
import com.quangtt.testengine.model.property.PropertyHandler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class TestSuite extends TestElement {
    private List<ITestCase> testCases = new ArrayList<>();

    private WebDriver webDriver;

    private IPropertyHandler propertyHandler = new PropertyHandler("TestSuite");

    public TestSuite(String name) {
        super(name);
    }

    public void addTestCase(ITestCase testCase) {
        testCase.getPropertyHandler().setNextHandler(this.propertyHandler);
        testCase.setTestSuite(this);
        testCases.add(testCase);
    }

    @Override
    public void run() {
        this.webDriver = new ChromeDriver();
        this.webDriver.manage().timeouts().pageLoadTimeout(Duration.of(30, ChronoUnit.MINUTES));
        for (ITestCase testCase:testCases) {
            testCase.run();
        }
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public IPropertyHandler getPropertyHandler() {
        return propertyHandler;
    }
}
