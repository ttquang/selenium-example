package com.example.test;

import com.example.exception.StepRuntimeException;
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

public class TestCase implements ITestCase {
    private List<Step> steps;

    private WebDriver webDriver;

    private Map<String, String> properties;

    public TestCase(List<Step> steps, WebDriver webDriver, Map<String, String> properties) {
        this.steps = steps;
        this.webDriver = webDriver;
        this.properties = properties;
    }

    public void run() {
        webDriver.manage().timeouts().pageLoadTimeout(Duration.of(30, ChronoUnit.MINUTES));
        webDriver.get("http://localhost:7001/clos/");
//        driver.get("http://172.24.1.90:7002/clos");

//        webDriver.switchTo().frame(0);

        for (Step step : steps) {
            try {
                step.run(this);
            } catch (StepRuntimeException ex) {
                throw new StepRuntimeException(step.getName(), step.getName());
            } catch (Exception ex) {
                throw new StepRuntimeException(step.getName(), step.getName());
            } finally {
                System.out.println(properties);
            }
        }
    }


    @Override
    public void visit(ClickStep step) {
        System.out.println(step);
        String selector = propertyParser(step.getSelector());
        findElement(selector).click();
    }

    @Override
    public void visit(DelayStep step) {
        System.out.println(step);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void visit(InputStep step) {
        System.out.println(step);
        String selector = propertyParser(step.getSelector());
        String value = propertyParser(step.getValue());
        findElement(selector).sendKeys(value);
    }

    @Override
    public void visit(InputSelectStep step) {
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
    public void visit(SetPropertyStep step) {
        System.out.println(step);
        String key = propertyParser(step.getKey());
        String value = propertyParser(step.getValue());
        properties.put(key, value);
    }

    @Override
    public void visit(TransferPropertyStep step) {
        System.out.println(step);
        String selector = propertyParser(step.getSelector());
        String key = propertyParser(step.getKey());
        String value = findElement(selector).getAttribute("value");
        System.out.println(key + "=========" + value);
        properties.put(key, value);
    }

    @Override
    public void visit(SwitchFrameStep step) {
        System.out.println(step);
        String selector = propertyParser(step.getSelector());
        webDriver.switchTo().frame(findElement(selector).getAttribute("id"));
    }

    private String propertyParser(String input) {
        String result = input;

        Pattern pattern = Pattern.compile("\\{(.*)}");
        Matcher m = pattern.matcher(input);
        if (m.find()) {
            if (properties.containsKey(m.group(1))) {
                result = input.replaceFirst("\\{(.*)}", properties.get(m.group(1)));
            }
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
