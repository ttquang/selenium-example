package com.quangtt.webtest.core.model;

import com.quangtt.webtest.core.exception.StepRuntimeException;
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

public class ExecutionEnvironment {
    WebDriver webDriver;

    public ExecutionEnvironment(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void delegate(TestStep testStep) {
        if (testStep.delayPeriod > 0) {
            try {
                Thread.sleep(testStep.delayPeriod);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
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

    private void execute(ClickElementTestStep testStep) {
        String selector = testStep.getProperty(testStep.selector);
        findElement(selector).click();
    }

    private void execute(ClickAllElementTestStep testStep) {
        String selector = testStep.getProperty(testStep.selector);
        for (WebElement element : findElements(selector)) {
            element.click();
        }
    }

    public void execute(InputElementTestStep testStep) {
        String selector = testStep.getProperty(testStep.selector);
        String value = testStep.getProperty(testStep.value);
        findElement(selector).sendKeys(value);
    }

    public void execute(InputSelectElementTestStep testStep) {
        String selector = testStep.getProperty(testStep.selector);
        Select select = new Select(findElement(selector));
        Pattern pattern = Pattern.compile("(.*)#(.*)");
        Matcher m = pattern.matcher(testStep.value);
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
            select.selectByVisibleText(testStep.value);
        }
    }

    public void execute(LoadPageTestStep testStep) {
        String url = testStep.getProperty(testStep.url);
        webDriver.get(url);
    }

    public void execute(SwitchFrameTestStep testStep) {
        String selector = testStep.getProperty(testStep.selector);
        webDriver.switchTo().frame(findElement(selector).getAttribute("id"));
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
