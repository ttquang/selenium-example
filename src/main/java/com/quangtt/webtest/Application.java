package com.quangtt.webtest;

import com.quangtt.webtest.api.ExcelImport;
import com.quangtt.webtest.core.exception.StepRuntimeException;
import com.quangtt.webtest.core.model.ExecutionEnvironment;
import com.quangtt.webtest.core.model.TestSuite;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.service.DriverService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class Application {
    public static void main(String[] args) throws FileNotFoundException {
        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, "D:\\webdriver\\chromedriver.exe");
        // redirect console output from chromedriver to the file chromedriver_log.txt in the target folder
        DriverService.Builder<ChromeDriverService, ChromeDriverService.Builder> serviceBuilder = new ChromeDriverService.Builder();
        ChromeDriverService chromeDriverService = serviceBuilder.build();
        chromeDriverService.sendOutputTo(new FileOutputStream("chromedriver_log.txt", true));
        WebDriver webDriver = null;
        try {
            ExcelImport excelImport = new ExcelImport();
            File file = new File("D:\\testTool\\testsuite\\TestSuite2.xlsx");
            TestSuite testSuite = excelImport.processTestSuit(file);
            ChromeOptions options = new ChromeOptions();
            options.setLogLevel(ChromeDriverLogLevel.INFO);

            webDriver = new ChromeDriver(chromeDriverService, options);
            webDriver.manage().timeouts().pageLoadTimeout(Duration.of(30, ChronoUnit.MINUTES));
            ExecutionEnvironment environment = new ExecutionEnvironment(webDriver);

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
