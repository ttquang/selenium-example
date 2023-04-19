package com.quangtt.webtest.execution;

import com.quangtt.webtest.core.model.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Map;

public class DefaultExecutionEnvironment extends TestRunner {
    WebDriver webDriver;

    public DefaultExecutionEnvironment(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public DefaultExecutionEnvironment(WebDriver webDriver, Map<String, String> properties) {
        this.webDriver = webDriver;
        constructPropertyHandler(PropertyLevel.ENVIRONMENT, properties);
    }

    protected WebElement findElement(String selector) {
        return this.webDriver.findElement(By.xpath(selector));
    }

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

    protected void select(WebElement webElement, String type, String value) {
        Select select = new Select(webElement);
        if ("Index".equals(type)) {
            select.selectByIndex(Integer.valueOf(value));
        } else if ("Label".equals(type)) {
            select.selectByVisibleText(value);
        } else if ("Value".equals(type)) {
            select.selectByValue(value);
        }
    }

    @Override
    public void execute(Step step) {
        if ("WebElement".equals(step.getType())) {
            String selector = step.getProperty(step.getParameters().get("selector"));
            WebElement webElement = findElement(selector);

            if ("SWITCH_TO".equals(step.getParameters().get("action"))) {
                this.webDriver.switchTo().frame(webElement);
            } else if ("CLICK".equals(step.getParameters().get("action"))) {
                webElement.click();
            } else if ("SEND_KEY".equals(step.getParameters().get("action"))) {
                String value = step.getProperty(step.getParameters().get("value"));
                webElement.sendKeys(value);
            } else if ("SELECT".equals(step.getParameters().get("action"))) {
                String type = "Value";
                String value = step.getProperty(step.getParameters().get("value"));
                select(selector, type, value);
            }

        } else if ("Navigation".equals(step.getType())) {
            String url = step.getProperty(step.getParameters().get("url"));
            this.webDriver.navigate().to(url);
        } else if ("Property".equals(step.getType())) {
            String selector = step.getProperty(step.getParameters().get("selector"));
            String value = findElement(selector).getAttribute("value");
            step.putProperty("{" + step.getParameters().get("target") + "}", value);
        }
    }

    @Override
    public void execute(ClickStep testStep) {
        String selector = testStep.getProperty(testStep.getSelector());
        findElement(selector).click();
    }

    @Override
    public void execute(ClickAllElementTestStep step) {
    }

    @Override
    public void execute(TextInputStep step) {
        String selector = step.getProperty(step.getSelector());
        String value = step.getProperty(step.getValue());
        findElement(selector).sendKeys(value);
    }

    @Override
    public void execute(SelectStep step) {
        String selector = step.getProperty(step.getSelector());
        String type = step.getSelectBy();
        String value = step.getProperty(step.getValue());
        select(selector, type, value);
    }

    @Override
    public void execute(NavigationToUrlStep step) {
        String url = step.getProperty(step.getUrl());
        this.webDriver.navigate().to(url);
    }

    @Override
    public void execute(SwitchToFrameByXpathStep step) {
        String selector = step.getProperty(step.getSelector());
        this.webDriver.switchTo().frame(findElement(selector));
    }

    @Override
    public void execute(PropertyTransferDOMValueStep step) {
        String selector = step.getProperty(step.getSelector());
        String value = findElement(selector).getAttribute(step.getAttribute());
        step.putProperty("{" + step.getTarget() + "}", value);
    }
}
