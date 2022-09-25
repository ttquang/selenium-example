package com.example;

import com.example.excel.ExcelUtil;
import com.example.exception.StepRuntimeException;
import com.example.test.ITestCase;
import com.example.test.Step;
import com.example.test.TestCase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver",
                "D:\\webdriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        try {
            ExcelUtil util = new ExcelUtil();
            File file = new File("D:\\Selenium-Testcase.xlsx");
            List<Step> steps = util.process(file);

            ITestCase testCase = new TestCase(steps, driver, new HashMap<>());
            testCase.run();
        } catch (StepRuntimeException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            driver.quit();
        }

    }
}
