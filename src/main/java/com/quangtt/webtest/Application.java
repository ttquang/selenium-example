package com.quangtt.webtest;

import com.quangtt.webtest.api.ExcelImport;
import com.quangtt.webtest.core.exception.StepRuntimeException;
import com.quangtt.webtest.core.model.ExecutionEnvironment;
import com.quangtt.webtest.core.model.TestSuite;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class Application {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "D:\\webdriver\\chromedriver.exe");
        WebDriver webDriver = null;
        try {
            ExcelImport excelImport = new ExcelImport();
            File file = new File("D:\\Selenium-Testcase-TestSuite.xlsx");
            TestSuite testSuite = excelImport.processTestSuit(file);
            webDriver = new ChromeDriver();
            webDriver.manage().timeouts().pageLoadTimeout(Duration.of(30, ChronoUnit.MINUTES));
            ExecutionEnvironment environment = new ExecutionEnvironment(webDriver);

            testSuite.runWith(environment);
        } catch (StepRuntimeException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            webDriver.quit();
        }

    }
}
