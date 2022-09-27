package com.quangtt.testengine;

import com.quangtt.testengine.exception.StepRuntimeException;
import com.quangtt.testengine.model.testelement.TestSuite;
import com.quangtt.testengine.util.ExcelUtil;

import java.io.File;

public class Application {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "D:\\webdriver\\chromedriver.exe");
//        WebDriver driver = new ChromeDriver();

        try {
            ExcelUtil util = new ExcelUtil();
            File file = new File("D:\\Selenium-Testcase-TestSuite.xlsx");
            TestSuite testSuite = util.processTestSuit(file);
            testSuite.run();
        } catch (StepRuntimeException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
//            driver.quit();
        }

    }
}
