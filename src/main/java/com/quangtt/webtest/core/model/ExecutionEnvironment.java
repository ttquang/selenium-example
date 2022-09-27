package com.quangtt.webtest.core.model;

import com.quangtt.testengine.exception.StepRuntimeException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class ExecutionEnvironment {
    WebDriver webDriver;

    public void delegate(TestStep testStep) {
        if (testStep instanceof ClickElementTestStep) {
            execute((ClickElementTestStep) testStep);
        } else if (testStep instanceof InputElementTestStep) {
            execute((InputElementTestStep) testStep);
        } else if (testStep instanceof SetPropertyTestStep) {
            execute((SetPropertyTestStep) testStep);
        } else if (testStep instanceof TransferPropertyTestStep) {
            execute((TransferPropertyTestStep) testStep);
        }
    }

    private void execute(ClickElementTestStep testStep) {
        String selector = testStep.getProperty(testStep.selector);
        findElement(selector).click();
    }

    public void execute(InputElementTestStep testStep) {
        String selector = testStep.getProperty(testStep.selector);
        String value = testStep.getProperty(testStep.value);
        findElement(selector).sendKeys(value);
    }

    public void execute(SetPropertyTestStep testStep) {
        String key = testStep.getProperty(testStep.key);
        String value = testStep.getProperty(testStep.value);
        testStep.propertyHandler.put(key, value);
    }

    public void execute(TransferPropertyTestStep testStep) {
        String selector = testStep.getProperty(testStep.selector);
        String key = testStep.getProperty(testStep.key);
        String value = findElement(selector).getAttribute("value");
        System.out.println(key + "=========" + value);
        testStep.putProperty(key, value);
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
