package com.quangtt.webtest.execution;

import com.quangtt.webtest.core.exception.StepRuntimeException;
import com.quangtt.webtest.core.model.*;
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
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultExecutionEnvironment extends ExecutionEnvironment {
    WebDriver webDriver;

    public DefaultExecutionEnvironment(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
    public DefaultExecutionEnvironment(WebDriver webDriver, Map<String, String> properties) {
        this.webDriver = webDriver;
        constructPropertyHandler(PropertyLevel.ENVIRONMENT, properties);
    }

    public void execute(ClickElementTestStep testStep) {
        String selector = testStep.getProperty(testStep.getSelector());
        findElement(selector).click();
    }

    public void execute(ClickAllElementTestStep testStep) {
        String selector = testStep.getProperty(testStep.getSelector());
        for (WebElement element : findElements(selector)) {
            element.click();
        }
    }

    public void execute(InputElementTestStep testStep) {
        String selector = testStep.getProperty(testStep.getSelector());
        String value = testStep.getProperty(testStep.getValue());
        findElement(selector).sendKeys(value);
    }

    public void execute(InputSelectElementTestStep testStep) {
        String selector = testStep.getProperty(testStep.getSelector());
        Select select = new Select(findElement(selector));
        Pattern pattern = Pattern.compile("(Index|Label|Value)#(.*)");
        Matcher m = pattern.matcher(testStep.getValue());
        if (m.find()) {
            String type = m.group(1);
            if ("Index".equals(type)) {
                Integer selectedIndex = Integer.valueOf(m.group(2));
                select.selectByIndex(selectedIndex);
            } else if ("Label".equals(type)) {
                String label = testStep.getProperty(m.group(2));
                select.selectByVisibleText(label);
            } else if ("Value".equals(type)) {
                String value = testStep.getProperty(m.group(2));
                select.selectByValue(value);
            }
        } else {
            select.selectByVisibleText(testStep.getValue());
        }
    }

    public void execute(LoadPageTestStep testStep) {
        String url = testStep.getProperty(testStep.getUrl());
        webDriver.get(url);
    }

    public void execute(SwitchFrameTestStep testStep) {
        String selector = testStep.getProperty(testStep.getSelector());
        webDriver.switchTo().frame(findElement(selector).getAttribute("id"));
    }

    public void execute(SetPropertyTestStep testStep) {
        String key = testStep.getProperty(testStep.getKey());
        String value = testStep.getProperty(testStep.getValue());
        testStep.putProperty(key, value);
    }

    public void execute(TransferPropertyTestStep testStep) {
        String selector = testStep.getProperty(testStep.getSelector());
        String key = testStep.getProperty(testStep.getKey());
        String value = findElement(selector).getAttribute("value");
        testStep.putProperty(key, value);
    }

    private List<WebElement> findElements(String selector) {
        try {
            FluentWait wait = new FluentWait(webDriver);
            wait.withTimeout(Duration.of(1000, ChronoUnit.MILLIS));
            wait.pollingEvery(Duration.of(250, ChronoUnit.MILLIS));
            wait.ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(selector)));
            return webDriver.findElements(By.xpath(selector));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new StepRuntimeException();
        }
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
            ex.printStackTrace();
            throw new StepRuntimeException();
        }
    }
}
