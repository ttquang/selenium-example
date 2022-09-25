package com.quangtt.testengine;

import com.quangtt.testengine.exception.StepRuntimeException;
import com.quangtt.testengine.util.ExcelUtil;
import com.quangtt.testengine.model.property.IPropertyHandler;
import com.quangtt.testengine.model.property.PropertyHandler;
import com.quangtt.testengine.model.testelement.ITestCase;
import com.quangtt.testengine.model.testelement.TestCase;
import com.quangtt.testengine.model.testelement.TestStep;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver",
                "D:\\webdriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        try {
            ExcelUtil util = new ExcelUtil();
            File file = new File("D:\\Selenium-Testcase.xlsx");
            List<TestStep> testSteps = util.process(file);

            IPropertyHandler testSuiteProperty = new PropertyHandler("TestSuite");
            IPropertyHandler testCaseProperty = new PropertyHandler("TestCase", testSuiteProperty);

            ITestCase testCase = new TestCase(testSteps, driver, testCaseProperty);
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
