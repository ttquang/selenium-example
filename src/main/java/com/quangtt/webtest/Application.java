package com.quangtt.webtest;

import com.quangtt.webtest.api.ExcelImport;
import com.quangtt.webtest.api.WebDriverFactory;
import com.quangtt.webtest.core.exception.StepRuntimeException;
import com.quangtt.webtest.core.model.ExecutionEnvironment;
import com.quangtt.webtest.core.model.TestSuite;
import com.quangtt.webtest.execution.DefaultExecutionEnvironment;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

import java.io.File;

public class Application {
    public static void main(String[] args) {
        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, "D:\\webdriver\\chromedriver.exe");
        WebDriver webDriver = null;
        try {
            ExcelImport excelImport = new ExcelImport();
            File file = new File("D:\\testTool\\testsuite\\TestSuite2.xlsx");
            TestSuite testSuite = excelImport.processTestSuit(file);

            webDriver = WebDriverFactory.get();
            ExecutionEnvironment environment = new DefaultExecutionEnvironment(webDriver);

            testSuite.runWith(environment);
        } catch (StepRuntimeException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                Thread.sleep(300000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            webDriver.quit();
        }

    }
}
