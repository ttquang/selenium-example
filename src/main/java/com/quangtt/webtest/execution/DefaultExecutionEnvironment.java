package com.quangtt.webtest.execution;

import com.quangtt.webtest.core.model.ExecutionEnvironment;
import com.quangtt.webtest.core.model.PropertyLevel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Map;

public class DefaultExecutionEnvironment extends ExecutionEnvironment {
    WebDriver webDriver;

    public DefaultExecutionEnvironment(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
    public DefaultExecutionEnvironment(WebDriver webDriver, Map<String, String> properties) {
        this.webDriver = webDriver;
        constructPropertyHandler(PropertyLevel.ENVIRONMENT, properties);
    }

    protected List<WebElement> findElements(String selector) {
        return this.webDriver.findElements(By.xpath(selector));
    }

    protected WebElement findElement(String selector) {
        return this.webDriver.findElement(By.xpath(selector));
    }

    @Override
    protected void get(String url) {
        this.webDriver.get(url);
    }

    @Override
    protected void switchTo(String selector) {
        WebElement element = findElement(selector);
        webDriver.switchTo().frame(element);
    }

    @Override
    protected void select(String selector, String type, String value) {
        Select select = new Select(findElement(selector));
        if ("Index".equals(type)) {
            select.selectByIndex(Integer.valueOf(value));
        } else if ("Label".equals(type)) {
            select.selectByVisibleText(value);
        } else if ("Value".equals(type)) {
            select.selectByValue(value);
        }
    }

    @Override
    protected void click(String selector) {
        findElement(selector).click();
    }

    @Override
    protected void clickAll(String selector) {
        findElements(selector).forEach(WebElement::click);
    }

    @Override
    protected void input(String selector, String value) {
        findElement(selector).sendKeys(value);
    }

    @Override
    protected String getAttribute(String selector, String attribute) {
        return findElement(selector).getAttribute(attribute);
    }
}
