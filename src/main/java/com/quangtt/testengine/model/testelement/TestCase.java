package com.quangtt.testengine.model.testelement;

import com.quangtt.testengine.exception.StepRuntimeException;
import com.quangtt.testengine.model.property.IPropertyHandler;
import com.quangtt.testengine.model.teststep.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestCase extends TestElement implements ITestCase {
    private List<TestStep> testSteps;

    private WebDriver webDriver;

    private IPropertyHandler propertyHandler;

    public TestCase(String name, List<TestStep> testSteps) {
        super(name);
        this.testSteps = testSteps;
    }

    public void setPropertyHandler(IPropertyHandler propertyHandler) {
        this.propertyHandler = propertyHandler;
    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void run() {
        webDriver.manage().timeouts().pageLoadTimeout(Duration.of(30, ChronoUnit.MINUTES));
//        webDriver.get("http://localhost:7001/clos/");
//        webDriver.get("http://192.168.1.24:7001/clos/");
//        driver.get("http://172.24.1.90:7002/clos");

        for (TestStep testStep : testSteps) {
            try {
                testStep.run(this);
            } catch (StepRuntimeException ex) {
                throw new StepRuntimeException(testStep.getName(), testStep.getName());
            } catch (Exception ex) {
                throw new StepRuntimeException(testStep.getName(), testStep.getName());
            } finally {
//                System.out.println(propertyHandler);
            }
        }
    }


    @Override
    public void visit(ClickTestStep step) {
        System.out.println(step);
        String selector = propertyParser(step.getSelector());
        findElement(selector).click();
    }

    @Override
    public void visit(DelayTestStep step) {
        System.out.println(step);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void visit(InputTestStep step) {
        System.out.println(step);
        String selector = propertyParser(step.getSelector());
        String value = propertyParser(step.getValue());
        findElement(selector).sendKeys(value);
    }

    @Override
    public void visit(InputSelectTestStep step) {
        System.out.println(step);
        String selector = propertyParser(step.getSelector());
        Select select = new Select(findElement(selector));
        Pattern pattern = Pattern.compile("(.*)#(.*)");
        Matcher m = pattern.matcher(step.getValue());
        if (m.find()) {
            String type = m.group(1);
            if ("Index".equals(type)) {
                Integer selectedIndex = Integer.valueOf(m.group(2));
                select.selectByIndex(selectedIndex);
            } else if ("Label".equals(type)) {
                String label = propertyParser(m.group(2));
                select.selectByVisibleText(label);
            } else if ("Value".equals(type)) {
                String value = propertyParser(m.group(2));
                select.selectByValue(value);
            }
        } else {
            select.selectByVisibleText(step.getValue());
        }
    }

    @Override
    public void visit(SetPropertyTestStep step) {
        System.out.println(step);
        String key = propertyParser(step.getKey());
        String value = propertyParser(step.getValue());
        propertyHandler.put(key, value);
    }

    @Override
    public void visit(TransferPropertyTestStep step) {
        System.out.println(step);
        String selector = propertyParser(step.getSelector());
        String key = propertyParser(step.getKey());
        String value = findElement(selector).getAttribute("value");
        System.out.println(key + "=========" + value);
        propertyHandler.put(key, value);
    }

    @Override
    public void visit(SwitchFrameTestStep step) {
        System.out.println(step);
        String selector = propertyParser(step.getSelector());
        webDriver.switchTo().frame(findElement(selector).getAttribute("id"));
    }

    @Override
    public void visit(LoadPageTestStep step) {
        System.out.println(step);
        String url = propertyParser(step.getUrl());
        webDriver.get(url);
    }

    private String propertyParser(String input) {
        String result = input;

        Pattern pattern = Pattern.compile("\\{(.*)}");
        Matcher m = pattern.matcher(input);
        if (m.find()) {
            result = input.replaceFirst("\\{(.*)}", propertyHandler.get(m.group(1)));
        }

        return result;
    }

    private WebElement findElement(String selector) {
        try {
            FluentWait wait = new FluentWait(webDriver);
            wait.withTimeout(Duration.of(1000, ChronoUnit.MILLIS));
            wait.pollingEvery(Duration.of(250, ChronoUnit.MILLIS));
            wait.ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(selector)));
            return webDriver.findElement(By.xpath(selector));
        } catch (Exception ex) {
            throw new StepRuntimeException();
        }

    }

}