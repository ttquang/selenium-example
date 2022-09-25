package com.example.test;

import com.example.TestCaseProperty;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebDriverVisitor implements IWebDriverVisitor {

    private WebDriver webDriver;

    public WebDriverVisitor(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Override
    public void click(ElementStep step) {
        findElement(step.getSelector()).click();
    }

    @Override
    public void input(InputStep step) {
        WebElement webElement = findElement(step.getSelector());
        Pattern pattern = Pattern.compile("\\{(.*)}");
        Matcher m = pattern.matcher(step.getValue());
        if (m.find()) {
            String propertyValue = TestCaseProperty.getInstance().get(m.group(1));
            if (Objects.nonNull(propertyValue)) {
                webElement.sendKeys(propertyValue);
            }
        } else {
            webElement.sendKeys(step.getValue());
        }
    }

    @Override
    public void select(InputStep step) {
        WebElement webElement = findElement(step.getSelector());
        Select select = new Select(webElement);
        Pattern pattern = Pattern.compile("(.*)#(.*)");
        Matcher m = pattern.matcher(step.getValue());
        if (m.find()) {
            String type = m.group(1);
            if ("Index".equals(type)) {
                Integer selectedIndex = Integer.valueOf(m.group(2));
                select.selectByIndex(selectedIndex);
            } else if ("Label".equals(type)) {
                String label = m.group(2);
                select.selectByVisibleText(label);
            }
        } else {
            select.selectByVisibleText(step.getValue());
        }
    }

    @Override
    public String value(String selector) {
        return webDriver.findElement(By.xpath(selector)).getAttribute("value");
    }

    private WebElement findElement(String selector) {
        String processedSelector = null;
        Pattern pattern = Pattern.compile("\\{(.*)}");
        Matcher m = pattern.matcher(selector);
        if (m.find()) {
            String propertyValue = TestCaseProperty.getInstance().get(m.group(1));
            if (Objects.nonNull(propertyValue)) {
                processedSelector = selector.replaceFirst("\\{(.*)}", propertyValue);
            }
        } else {
            processedSelector = selector;
        }


        FluentWait wait = new FluentWait(webDriver);
        wait.withTimeout(Duration.of(5000, ChronoUnit.MILLIS));
        wait.pollingEvery(Duration.of(250, ChronoUnit.MILLIS));
        wait.ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(processedSelector)));
        return webDriver.findElement(By.xpath(processedSelector));
    }

}
